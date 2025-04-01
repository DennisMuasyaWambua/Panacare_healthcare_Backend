package com.panacare.frontend.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DoctorRest {
    private String doctorId;
    private String licenseNumber;
    private BigDecimal consultationFee;
    private int yearsOfExperience;
    private String speciality;
    private String location;
    private Boolean verified;
}
