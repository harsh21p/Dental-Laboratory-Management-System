package com.dental.lab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "lab_material")
@Data
@JsonIgnoreProperties({"lab"})
public class LabMaterial {

    @Embeddable
    @Getter
    @Setter
    public static class LabMaterialId implements Serializable {
        @Column(name = "lab_id")
        private String labId;

        @Column(name = "material_id")
        private String materialId;

    }

    @EmbeddedId
    private LabMaterialId id;

    @ManyToOne
    @MapsId("labId")
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name = "price")
    private Double price;

}
