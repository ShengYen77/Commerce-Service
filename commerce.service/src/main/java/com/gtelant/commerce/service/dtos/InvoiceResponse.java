package com.gtelant.commerce.service.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceResponse {
    private Integer invoiceId;
    private String invoiceNumber;
    private BigDecimal amount;
    private BigDecimal deliveryFee;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private LocalDateTime issuedDate;
    private String status;
}


