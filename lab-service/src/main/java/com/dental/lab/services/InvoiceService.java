package com.dental.lab.services;

import com.dental.lab.config.InvoiceSpecification;
import com.dental.lab.dto.PagedResponse;
import com.dental.lab.model.Entry;
import com.dental.lab.model.Invoice;
import com.dental.lab.repository.EntryRepository;
import com.dental.lab.repository.InvoiceRepository;
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
@Transactional
@RequiredArgsConstructor
public class InvoiceService {
    private final EntryRepository entryRepository;
    private final InvoiceRepository invoiceRepository;

    public Invoice createInvoice(List<String> entryIds, Date invoiceDate) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(invoiceDate);

        List<Entry> entries = entryRepository.findAllById(entryIds);
        for (Entry entry : entries) {
            invoice.addEntry(entry);
        }

        return invoiceRepository.save(invoice);
    }

    public Invoice getInvoiceById(String id) throws Exception {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            return invoice.get();
        }else {
            throw new NotFoundException("Invoice not found");
        }
    }

    public PagedResponse<Invoice> getFilteredInvoices(Date startDate, Date endDate, String labId, String doctorId, List<String> entryIds, Integer page, Integer size) throws Exception {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Invoice> labPage = invoiceRepository.findAll(InvoiceSpecification.filterByParameters(startDate, endDate, labId, doctorId, entryIds), pageable);
            return new PagedResponse<Invoice>(
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
}
