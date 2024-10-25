package com.dental.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"userId","labs"})
public class Doctor {
     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private String id;

     @Column(unique = true)
     private String userId;

     private String firstName;
     private String lastName;

     @Column(unique = true)
     private String phone;

     @Column(unique = true)
     private String email;

     @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
     @JoinTable(
             name = "doctor_lab",
             joinColumns = @JoinColumn(name = "doctor_id"),
             inverseJoinColumns = @JoinColumn(name = "lab_id")
     )
     @JsonIgnoreProperties({"doctors","userId"})
     private Set<Lab> labs = new HashSet<>();

     public void addLab(Lab lab) {
          this.labs.add(lab);
          lab.getDoctors().add(this);
     }

     public void removeLab(Lab lab) {
          this.labs.remove(lab);
          lab.getDoctors().remove(this);
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Doctor doctor = (Doctor) o;
          return Objects.equals(id, doctor.id);
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
