package com.panacare.frontend.model.request;

import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.shared.HealthCareCategories;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HealthCareRequest {
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
    private List<DoctorDto> doctors;

    @Override
    public String toString() {
        return "SystemUser{" +
                "healthCareId='" + healthCareId + '\'' +
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", location='" + location + '\'' +
                ", county='" + county + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}


