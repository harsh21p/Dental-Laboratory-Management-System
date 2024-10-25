package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.FilterRequest;
import com.dental.lab.dto.InvoiceRequest;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.Invoice;
import com.dental.lab.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/lab/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<ApiResponse<Invoice>> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            ApiResponse<Invoice> response = new ApiResponse<>(200,false, "Data fetched successfully", invoiceService.createInvoice(invoiceRequest.getEntryIds(), invoiceRequest.getInvoiceDate(),invoiceRequest.getLabId(), invoiceRequest.getDoctorId()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Invoice> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Invoice>> getInvoiceById(@PathVariable String id) {
        try {
            ApiResponse<Invoice> response = new ApiResponse<>(200,false, "Data fetched successfully", invoiceService.getInvoiceById(id));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Invoice> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/get-invoice-filter")
    public ResponseEntity<ApiResponse<PagedResponse<Invoice>>> getAllInvoices(@RequestBody FilterRequest filterRequest) {
        try {
            ApiResponse<PagedResponse<Invoice>> response = new ApiResponse<>(200,false, "Data fetched successfully", invoiceService.getFilteredInvoices(filterRequest.getStartDate(),filterRequest.getEndDate(),filterRequest.getLabId(),filterRequest.getDoctorId(),filterRequest.getEntries(),filterRequest.getPage(),filterRequest.getSize()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Invoice>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
