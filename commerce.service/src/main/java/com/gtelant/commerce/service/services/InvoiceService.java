package com.gtelant.commerce.service.services;

import com.gtelant.commerce.service.dtos.InvoiceRequest;
import com.gtelant.commerce.service.dtos.InvoiceResponse;
import com.gtelant.commerce.service.mappers.InvoiceMapper;
import com.gtelant.commerce.service.models.Invoice;
import com.gtelant.commerce.service.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::toResponse)
                .toList();
    }

    public Optional<InvoiceResponse> getInvoiceById(Integer id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toResponse);
    }

    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = invoiceMapper.toEntity(request);
        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }

    public InvoiceResponse updateInvoice(Integer id, InvoiceRequest request) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));

        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setAmount(request.getAmount());
        invoice.setDeliveryFee(request.getDeliveryFee());
        invoice.setTaxRate(request.getTaxRate());
        invoice.setIssuedDate(request.getIssuedDate());
        invoice.setStatus(request.getStatus());

        return invoiceMapper.toResponse(invoiceRepository.save(invoice));
    }

    public void deleteInvoice(Integer id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));
        invoiceRepository.delete(invoice);
    }
}

