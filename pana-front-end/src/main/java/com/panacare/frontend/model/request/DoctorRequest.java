package com.panacare.frontend.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DoctorRequest {
    private String doctorId;
    @NotNull(message = "License Number is required.")
    private String licenseNumber;
    private BigDecimal consultationFee;
    private int yearsOfExperience;
    private String speciality;
    private String location;
    private Boolean verified;
    private String userId;
    private Integer healthCareId;

    @Override
    public String toString() {
        return "SystemUser{" +
                "doctorId='" + doctorId + '\'' +
                "licenseNumber='" + licenseNumber + '\'' +
                "consultationFee='" + consultationFee + '\'' +
                ", yearsOfExperience='" + yearsOfExperience + '\'' +
                ", speciality='" + speciality + '\'' +
                ", location='" + location + '\'' +
                ", verified='" + verified + '\'' +
                '}';
    }
}


