package com.panacare.panabeans.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.shared.HealthCareCategories;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_panacare_health_care")
public class HealthCareEntity extends StandardEntity {

    @Column(unique = true, nullable = false)
    private String healthCareId;
    private String name;
    private String description;
    private String phoneNumber;
    private String email;
    private String website;
    private String location;
    private String county;
    private String workingHours;
    private HealthCareCategories category;
    @OneToMany(mappedBy = "healthCare", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevent infinite recursion during serialization
    private Set<DoctorEntity> doctors;
}
