package com.panacare.panabeans.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserDto {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String title;
    private String country;
    private String imageUrl;
    private DoctorDto doctorDto;
    private String phoneNumber;
    private Date dateRegistered;
    private List<RoleDto> roles;
}
