package com.panacare.panabeans.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_panacare_doctor")
public class DoctorEntity extends StandardEntity {

    @Column(unique = true, nullable = false)
    private String doctorId;
    private String licenseNumber;
    private BigDecimal consultationFee;
    private int yearsOfExperience;
    private String speciality;
    private String location;
    @OneToOne(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonManagedReference
    private UserEntity user;
    private Boolean verified;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVerified;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_care_id")
    @JsonBackReference // Prevent infinite recursion during serialization
    private HealthCareEntity healthCare;

}
