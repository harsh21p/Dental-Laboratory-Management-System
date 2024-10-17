package com.dental.doctor.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "entries")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lab_id", nullable = false)
    private Lab lab;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material Material;

    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}