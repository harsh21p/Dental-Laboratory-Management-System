package com.dental.doctor.model;

import com.dental.doctor.model.Lab;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "balance")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lab_id", nullable = false)
    private Lab lab;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "balance")
    private Double balance;
}
