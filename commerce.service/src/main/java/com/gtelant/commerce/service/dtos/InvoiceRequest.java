package com.gtelant.commerce.service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceRequest {
    private String invoiceNumber;
    private BigDecimal amount;
    private BigDecimal deliveryFee;
    private BigDecimal taxRate;
    private LocalDateTime issuedDate;
    private String status;
}
