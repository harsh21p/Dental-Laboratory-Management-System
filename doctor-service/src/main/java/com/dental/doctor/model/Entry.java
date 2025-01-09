package com.dental.doctor.model;


import com.dental.doctor.model.Lab;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private LabMaterial labMaterial;

    @Column(name = "amount")
    private Double amount;

    private Integer unit;

    private String patient;
    @Column(name = "entry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    @ElementCollection
    private List<String> graph;


    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @Column(name = "deleted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnoreProperties({"invoice","entries"})
    private Invoice invoice;

}