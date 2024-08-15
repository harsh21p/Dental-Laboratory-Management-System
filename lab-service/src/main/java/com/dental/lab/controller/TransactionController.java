package com.dental.lab.controller;

import com.dental.lab.dto.ApiResponse;
import com.dental.lab.dto.PagedResponse;
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
    public ResponseEntity<ApiResponse<Transaction>> createTransaction(@RequestBody Transaction transaction) {
        try {
            ApiResponse<Transaction> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.createTransaction(transaction.getDoctor().getId(), transaction.getLab().getId(), transaction.getTransactionDate(), transaction.getAmount()));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Transaction> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<Transaction>>> getAllTransactions(@RequestParam(defaultValue = "0",required = false) int page,@RequestParam(defaultValue = "10",required = false) int size) {
        try {
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.getAllTransactions(page, size));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/by-doctor")
    public ResponseEntity<ApiResponse<PagedResponse<Transaction>>> getTransactionsByDoctor(@RequestParam String doctorId,@RequestParam(defaultValue = "0",required = false) int page,@RequestParam(defaultValue = "10",required = false) int size) {
        try {
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.getTransactionsByDoctor(doctorId,page, size));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/by-lab")
    public ResponseEntity<ApiResponse<PagedResponse<Transaction>>> getTransactionsByLab(@RequestParam String labId,@RequestParam(defaultValue = "0",required = false) int page,@RequestParam(defaultValue = "10",required = false) int size) {
        try {
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.getTransactionsByLab(labId,page, size));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<PagedResponse<Transaction>> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @GetMapping("/by-lab-doctor-last")
    public ResponseEntity<ApiResponse<Transaction>> getLastTransactions(@RequestParam String labId,@RequestParam String doctorId) {
        try {
            ApiResponse<Transaction> response = new ApiResponse<>(200,false, "Data fetched successfully", transactionService.getLastTransaction(labId,doctorId));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception exception){
            ApiResponse<Transaction> response = new ApiResponse<>(200,true, "Failed to fetch data: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
