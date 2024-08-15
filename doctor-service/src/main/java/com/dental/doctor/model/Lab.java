package com.dental.doctor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "labs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"userId","doctors"})
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

    @ManyToMany(mappedBy = "labs", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonIgnoreProperties({"labs","userId"})
    private Set<Doctor> doctors = new HashSet<>();

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.getLabs().add(this);
    }

    public void removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.getLabs().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lab lab = (Lab) o;
        return Objects.equals(id, lab.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}