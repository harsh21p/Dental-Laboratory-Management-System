package com.dental.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "labs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"userId","doctors","labMaterials"})
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
    private Set<Doctor> doctors = new HashSet<>();

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.getLabs().add(this);
    }

    public void removeDoctor(Doctor doctor) {
         this.doctors.remove(doctor);
         doctor.getLabs().remove(this);
    }

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<LabMaterial> labMaterials = new HashSet<>();

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

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "deleted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted;

}
