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

     // reference from enrollment service
     @Column(unique = true)
     private String userId;

     private String firstName;
     private String lastName;


     @Column(unique = true)
     private String phone;

     @Column(unique = true)
     private String email;

}
