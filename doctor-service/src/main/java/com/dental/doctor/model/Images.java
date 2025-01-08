package com.dental.doctor.model;

import com.dental.doctor.model.Lab;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
@JsonIgnoreProperties("lab")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID )
    private String id;

    @Lob
    @Column(name = "logo_image", columnDefinition = "LONGBLOB")
    private byte[] logoImage;

    @Lob
    @Column(name = "signature_image", columnDefinition = "LONGBLOB")
    private byte[] signatureImage;

    @OneToOne
    @JoinColumn(name = "lab_id", referencedColumnName = "id")
    private Lab lab;
}
