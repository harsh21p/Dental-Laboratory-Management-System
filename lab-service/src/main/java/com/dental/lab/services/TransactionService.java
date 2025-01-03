package com.dental.lab.services;

import com.dental.lab.config.EntrySpecification;
import com.dental.lab.config.TransactionSpecification;
import com.dental.lab.dto.FilterRequest;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.*;
import com.dental.lab.repository.DoctorLabRepository;
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

    public Transaction createTransaction(String doctorId, String labId, Date transactionDate, Double amount,String reason) throws Exception {
        try {
            Doctor doctor = labService.getDoctorById(doctorId);
            Lab lab = labService.getLabById(labId);
            Transaction transaction = new Transaction();
            transaction.setDoctor(doctor);
            transaction.setLab(lab);
            transaction.setTransactionDate(transactionDate);
            transaction.setCreated(new Date());
            transaction.setAmount(amount);
            transaction.setReason(reason);
            changeBalance(amount, lab, doctor);
            return transactionRepository.save(transaction);
        } catch (Exception exception){
            throw exception;
        }

    }

    private final DoctorLabRepository doctorLabRepository;
    private final DoctorLabService doctorLabService;

    private void changeBalance(Double amount, Lab lab, Doctor doctor) {
        Optional<Balance> doctorLab = doctorLabService.findByDoctorIdAndLabId(doctor.getId(),lab.getId());
        if(doctorLab.isPresent()){
            doctorLab.get().setBalance(doctorLab.get().getBalance() + amount);
            doctorLabRepository.save(doctorLab.get());
        } else {
            Balance balanceNew = Balance.builder().balance(amount).doctor(doctor).lab(lab).build();
            doctorLabRepository.save(balanceNew);
        }
    }


    public  PagedResponse<Transaction> getTransactionsByFilter(FilterRequest filterRequest) throws Exception {
        try {
            Pageable pageable = PageRequest.of(filterRequest.getPage(), filterRequest.getSize());
            Page<Transaction> transactions = transactionRepository.findAll(TransactionSpecification.filterByParameters(filterRequest.getStartDate(), filterRequest.getEndDate(), filterRequest.getLabId(), filterRequest.getDoctorId(),filterRequest.getEntryId(),filterRequest.getInvoiceId()), pageable);
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


}
