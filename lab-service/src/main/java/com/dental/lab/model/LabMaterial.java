package com.dental.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "lab_material")
@Data
@JsonIgnoreProperties({"lab"})
public class LabMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "price")
    private Double price;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "deleted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted;

}
