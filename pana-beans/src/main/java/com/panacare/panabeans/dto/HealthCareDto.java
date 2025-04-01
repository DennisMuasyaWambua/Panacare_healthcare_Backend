package com.panacare.panabeans.dto;

import com.panacare.panabeans.shared.HealthCareCategories;

import java.util.List;

public class HealthCareDto {
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

    public String getHealthCareId() {
        return healthCareId;
    }

    public void setHealthCareId(String healthCareId) {
        this.healthCareId = healthCareId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public HealthCareCategories getCategory() {
        return category;
    }

    public void setCategory(HealthCareCategories category) {
        this.category = category;
    }

    public List<DoctorDto> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDto> doctors) {
        this.doctors = doctors;
    }
}
