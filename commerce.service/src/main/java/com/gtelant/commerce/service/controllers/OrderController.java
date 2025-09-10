package com.gtelant.commerce.service.controllers;

import com.gtelant.commerce.service.dtos.OrderItemRequest;
import com.gtelant.commerce.service.dtos.OrderItemResponse;
import com.gtelant.commerce.service.dtos.OrderRequest;
import com.gtelant.commerce.service.dtos.OrderResponse;
import com.gtelant.commerce.service.mappers.OrderItemMapper;
import com.gtelant.commerce.service.mappers.OrderMapper;
import com.gtelant.commerce.service.models.*;
import com.gtelant.commerce.service.repositories.ProductRepository;
import com.gtelant.commerce.service.repositories.UserRepository;
import com.gtelant.commerce.service.services.OrderItemService;
import com.gtelant.commerce.service.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderController(OrderService orderService,
                           OrderItemService orderItemService,
                           OrderMapper orderMapper,
                           OrderItemMapper orderItemMapper,
                           UserRepository userRepository,
                           ProductRepository productRepository) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // 建立訂單
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 將 DTO 轉成 Entity 並計算 totalPrice
        List<OrderItem> items = request.getItems().stream()
                .map(itemReq -> {
                    Product product = productRepository.findById(itemReq.getProductId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found: " + itemReq.getProductId()));
                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(itemReq.getQuantity());
                    item.setUnitPrice(product.getPrice());
                    item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));
                    return item;
                })
                .collect(Collectors.toList());

        Order order = orderMapper.toEntity(request, user, items);
        // 訂單總額統一計算
        BigDecimal totalAmount = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderService.createOrder(order);
        return new ResponseEntity<>(orderMapper.toResponse(savedOrder), HttpStatus.CREATED);
    }

    // 其他訂單操作
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Integer userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Integer orderId,
                                                           @RequestParam OrderStatus status) {
        Order order = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }

    // 訂單明細管理
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderItemResponse> addItemToOrder(@PathVariable Integer orderId,
                                                            @RequestBody OrderItemRequest request) {
        OrderItem item = orderItemService.addItemToOrder(orderId, request.getProductId(), request.getQuantity());
        // 新增後重新計算訂單總額
        Order order = orderService.getOrderById(orderId);
        BigDecimal totalAmount = order.getOrderItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        orderService.createOrder(order); // 儲存更新後的總額

        return new ResponseEntity<>(orderItemMapper.toResponse(item), HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Integer itemId) {
        OrderItem item = orderItemService.getItemsByOrderId(itemId)
                .stream().findFirst()
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found: " + itemId));

        Integer orderId = item.getOrder().getId();
        orderItemService.removeItem(itemId);

        // 刪除後重新計算訂單總額
        Order order = orderService.getOrderById(orderId);
        BigDecimal totalAmount = order.getOrderItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        orderService.createOrder(order); // 儲存更新後的總額

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponse>> getItemsByOrder(@PathVariable Integer orderId) {
        List<OrderItem> items = orderItemService.getItemsByOrderId(orderId);
        return ResponseEntity.ok(items.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList()));
    }
}
