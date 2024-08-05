package com.dental.lab.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "labs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lab {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String userId;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;
}
