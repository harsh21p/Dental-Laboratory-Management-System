package com.dental.doctor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private String id;
     private String name;
     private String surname;
     private String phone;
     private String email;
     private String role;
}
