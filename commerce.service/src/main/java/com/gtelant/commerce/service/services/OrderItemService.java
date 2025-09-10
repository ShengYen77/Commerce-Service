package com.gtelant.commerce.service.services;

import com.gtelant.commerce.service.models.Order;
import com.gtelant.commerce.service.models.OrderItem;
import com.gtelant.commerce.service.models.Product;
import com.gtelant.commerce.service.repositories.OrderItemRepository;
import com.gtelant.commerce.service.repositories.OrderRepository;
import com.gtelant.commerce.service.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemService(OrderItemRepository orderItemRepository,
                            OrderRepository orderRepository,
                            ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderItem addItemToOrder(Integer orderId, Integer productId, Integer quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + productId));

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);

        // 計算 unitPrice 與 totalPrice
        BigDecimal unitPrice = product.getPrice();
        item.setUnitPrice(unitPrice);
        item.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));

        // 儲存後更新訂單總金額
        OrderItem savedItem = orderItemRepository.save(item);
        order.setTotalAmount(order.getOrderItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderRepository.save(order);

        return savedItem;
    }



    @Transactional
    public void removeItem(Integer orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new EntityNotFoundException("OrderItem not found: " + orderItemId);
        }
        orderItemRepository.deleteById(orderItemId);
    }

    public List<OrderItem> getItemsByOrderId(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
