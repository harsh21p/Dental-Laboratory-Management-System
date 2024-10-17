package com.dental.doctor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"entries"})
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "invoice_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Entry> entries = new HashSet<>();

    public void addEntry(Entry entry) {
        entries.add(entry);
        entry.setInvoice(this);
    }

    public void removeEntry(Entry entry) {
        entries.remove(entry);
        entry.setInvoice(null);
    }

}
