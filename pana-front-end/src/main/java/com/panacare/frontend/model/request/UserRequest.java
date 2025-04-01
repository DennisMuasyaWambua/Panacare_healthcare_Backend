package com.panacare.frontend.model.request;

import com.panacare.frontend.model.response.DoctorRest;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserRequest {
    private String userId;
    @NotNull(message = "First Name is required.")
    private String username;
    @NotNull(message = "First Name is required.")
    private String firstName;
    @NotNull(message = "Last Name is required.")
    private String lastName;
    private String email;
    private String password;

    private DoctorRest doctorDto;
    private String phoneNumber;
    private String title;
    private String country;
    private String imageUrl;
    private List<RoleRequestModel> roles;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DoctorRest getDoctorDto() {
        return doctorDto;
    }

    public void setDoctorDto(DoctorRest doctorDto) {
        this.doctorDto = doctorDto;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<RoleRequestModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleRequestModel> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "userId='" + userId + '\'' +
                "username='" + username + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", roles=" + roles +
                '}';
    }
}


