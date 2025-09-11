package com.gtelant.commerce.service.mappers;

import com.gtelant.commerce.service.dtos.InvoiceRequest;
import com.gtelant.commerce.service.dtos.InvoiceResponse;
import com.gtelant.commerce.service.models.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {

    // DTO → Entity
    public Invoice toEntity(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setAmount(request.getAmount());
        invoice.setDeliveryFee(request.getDeliveryFee());
        invoice.setTaxRate(request.getTaxRate());
        invoice.setIssuedDate(request.getIssuedDate());
        invoice.setStatus(request.getStatus());
        return invoice;
    }

    // Entity → DTO
    public InvoiceResponse toResponse(Invoice invoice) {
        InvoiceResponse dto = new InvoiceResponse();
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setAmount(invoice.getAmount());
        dto.setDeliveryFee(invoice.getDeliveryFee());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setIssuedDate(invoice.getIssuedDate());
        dto.setStatus(invoice.getStatus());

        return dto;
    }
}

