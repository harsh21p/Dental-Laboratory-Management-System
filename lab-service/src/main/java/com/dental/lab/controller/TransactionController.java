package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.FilterRequest;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.dto.TransactionRequest;
import com.dental.lab.model.Transaction;
import com.dental.lab.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lab/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        try {
            ApiResponse<Transaction> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.createTransaction(transactionRequest.getDoctorId(), transactionRequest.getLabId(), transactionRequest.getDate(), transactionRequest.getAmount(),transactionRequest.getReason()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Transaction> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/by-filter")
    public ResponseEntity<ApiResponse<PagedResponse<Transaction>>> getTransactionsByParam(@RequestBody FilterRequest filterRequest) {
        try {
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.getTransactionsByFilter(filterRequest));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
