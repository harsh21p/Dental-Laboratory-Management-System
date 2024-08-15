package com.dental.lab.services;

import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.Doctor;
import com.dental.lab.model.Lab;
import com.dental.lab.model.Transaction;
import com.dental.lab.repository.LabRepository;
import com.dental.lab.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final LabRepository labRepository;
    private final LabService labService;

    public Transaction createTransaction(String doctorId, String labId, Date transactionDate, Double amount) throws Exception {
        try {
            Transaction transaction = new Transaction();
            transaction.setDoctor(labService.getDoctorById(doctorId));
            transaction.setLab(labRepository.findById(labId).orElseThrow(() -> new IllegalArgumentException("Lab not found")));
            transaction.setTransactionDate(transactionDate);
            transaction.setCreated(new Date());
            transaction.setAmount(amount);
            transaction.setBalance(setBalance(doctorId,labId,amount));
            return transactionRepository.save(transaction);
        } catch (Exception exception){
            throw exception;
        }

    }

    private Double setBalance(String doctorId, String labId, double amount) throws Exception{
        try {
           Transaction transaction = getLastTransaction(labId,doctorId);
           double balance = transaction.getBalance();
           balance = balance + amount;
           return balance;
        } catch (Exception exception){
            if (Objects.equals(exception.getMessage(), "Transaction not found")){
                return amount;
            }else {
                throw exception;
            }
        }
    }

    public PagedResponse<Transaction> getAllTransactions(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Transaction> transactions = transactionRepository.findAll(pageable);
            return new PagedResponse<Transaction>(
                    transactions.getContent(),
                    transactions.getNumber(),
                    transactions.getSize(),
                    transactions.getTotalElements(),
                    transactions.getTotalPages(),
                    transactions.isLast()
            );
        } catch (Exception exception){
            throw exception;
        }
    }

    public PagedResponse<Transaction> getTransactionsByDoctor(String doctorId,int page, int size) throws Exception{
        try {
            Doctor doctor = labService.getDoctorById(doctorId);
            Pageable pageable = PageRequest.of(page, size);
                Page<Transaction> transactions = transactionRepository.findByDoctor(doctor,pageable);
                return new PagedResponse<Transaction>(
                        transactions.getContent(),
                        transactions.getNumber(),
                        transactions.getSize(),
                        transactions.getTotalElements(),
                        transactions.getTotalPages(),
                        transactions.isLast()
                );

        } catch (Exception e) {
            throw e;
        }
    }

    public  PagedResponse<Transaction> getTransactionsByLab(String labId, int page, int size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Lab lab = labService.getLabById(labId);
            Page<Transaction> transactions =   transactionRepository.findByLab(lab,pageable);
            return new PagedResponse<Transaction>(
                    transactions.getContent(),
                    transactions.getNumber(),
                    transactions.getSize(),
                    transactions.getTotalElements(),
                    transactions.getTotalPages(),
                    transactions.isLast()
            );
        } catch (Exception e) {
            throw e;
        }
    }

    public Transaction getLastTransaction(String labId, String doctorId) throws Exception {
        try {
            Page<Transaction> transactions = transactionRepository.findLastTransactionByLabAndDoctor(labId,doctorId,PageRequest.of(0, 1));
            if(!transactions.getContent().isEmpty()) {
                return transactions.getContent().get(0);
            } else {
                throw new Exception("Transaction not found");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
