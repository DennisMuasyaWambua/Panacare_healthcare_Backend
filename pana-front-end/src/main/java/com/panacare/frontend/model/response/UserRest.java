package com.panacare.frontend.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRest {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private String country;
    private String imageUrl;
    private String phoneNumber;
}
