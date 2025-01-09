package com.dental.lab.services;

import com.dental.lab.config.EntrySpecification;
import com.dental.lab.dto.FilterRequest;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.*;
import com.dental.lab.repository.*;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EntryService {
    private final EntryRepository entryRepository;
    private final LabRepository labRepository;
    private final MaterialRepository materialRepository;
    private final LabService labService;
    private final LabMaterialRepository labMaterialRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final InvoiceRepository invoiceRepository;
    private final DoctorLabService doctorLabService;
    private final DoctorLabRepository doctorLabRepository;

    public Entry addEntry(String labId, String doctorId, String materialId, Date entryDate, Double amount, List<String> graph, Integer unit, String patient) throws Exception {
        try {
            Optional<Lab> labOpt = labRepository.findById(labId);
            Optional<LabMaterial> materialOpt = labMaterialRepository.findById(materialId);
            Doctor doctor = labService.getDoctorById(doctorId);
            if (labOpt.isPresent() && materialOpt.isPresent()) {
                Entry entry = new Entry();
                entry.setLab(labOpt.get());
                entry.setLabMaterial(materialOpt.get());
                entry.setEntryDate(entryDate);
                entry.setDoctor(doctor);
                entry.setAmount(amount);
                entry.setGraph(graph);
                entry.setCreated(new Date());
                entry.setUnit(unit);
                entry.setPatient(patient);
                transactionService.createTransaction(entry.getDoctor().getId(),entry.getLab().getId(),entry.getEntryDate(),entry.getAmount(),"This is an new entry");
                return entryRepository.save(entry);
            } else {
                throw new IllegalArgumentException("Lab, Doctor, or Material not found");
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public Entry getEntryById(String id) throws Exception {
        Optional<Entry> entry = entryRepository.findById(id);
        if (entry.isPresent()) {
            return entry.get();
        }else {
            throw new NotFoundException("Invoice not found");
        }
    }

    public PagedResponse<Entry> getEntriesByFilter(Date startDate, Date endDate,List<String> doctorId,String labId,int page, int size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Entry> labPage = entryRepository.findAll(EntrySpecification.filterByParameters(startDate, endDate, labId, doctorId), pageable);
            return new PagedResponse<Entry>(
                    labPage.getContent(),
                    labPage.getNumber(),
                    labPage.getSize(),
                    labPage.getTotalElements(),
                    labPage.getTotalPages(),
                    labPage.isLast()
            );
        } catch (Exception exception){
            throw exception;
        }
    }

    public Entry updateEntry(String entryId, String doctorId, String materialId, Date entryDate, Double amount, List<String> graph, Integer unit, String patient) throws Exception {
        try {
            Optional<Entry> entryOpt = entryRepository.findById(entryId);
            Optional<LabMaterial> materialOpt = labMaterialRepository.findById(materialId);
            Doctor doctor = labService.getDoctorById(doctorId);
            if (entryOpt.isPresent() && materialOpt.isPresent()) {
                Entry entry = entryOpt.get();
                entry.setLabMaterial(materialOpt.get());
                entry.setEntryDate(entryDate);
                entry.setDoctor(doctor);
                entry.setAmount(amount);
                entry.setGraph(graph);
                entry.setUnit(unit);
                entry.setPatient(patient);
                // Update value
                double value = entry.getAmount();
                value = amount - value;
                transactionService.createTransaction(entry.getDoctor().getId(),entry.getLab().getId(),entry.getEntryDate(),value,"This is Updated value for the entry");
                return entryRepository.save(entry);
            } else {
                throw new IllegalArgumentException("Lab, Doctor, or Material not found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public String deleteEntries(String id) throws Exception {
        try {
            Optional<Entry> entry = entryRepository.findById(id);
            if(entry.isPresent() && entry.get().getInvoice() == null) {
                PagedResponse<Transaction> transactions = transactionService.getTransactionsByFilter(FilterRequest.builder().entryId(id).page(0).size(1000).build());
                for (Transaction transaction : transactions.getContent()) {
                    transaction.setEntry(null);
                    transactionRepository.save(transaction);
                }
                transactionService.createTransaction(
                        entry.get().getDoctor().getId(),entry.get().getLab().getId(),
                        new Date(),
                        entry.get().getAmount() * -1,
                        "Entry Deletion"
                );
                entryRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Entry is associated with invoice please delete the invoice first");
            }
            return id;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
