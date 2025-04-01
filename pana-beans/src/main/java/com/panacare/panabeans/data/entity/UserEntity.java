package com.panacare.panabeans.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.panacare.panabeans.shared.DatabaseAESConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_framework_user")
public class UserEntity extends StandardEntity {

    @Column(unique = true, nullable = false)
    private String userId;

    private String username;

//    @Convert(converter = DatabaseAESConverter.class)
    private String firstName;

//    @Convert(converter = DatabaseAESConverter.class)
    private String lastName;

//    @Convert(converter = DatabaseAESConverter.class)
    private String email;

    private String title;

    private String country;

    private String imageUrl;

    private Boolean accountActivated;

//    @Convert(converter = DatabaseAESConverter.class)
    private String phoneNumber;

    private String verificationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAccountActivated;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tbl_framework_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonBackReference
    private DoctorEntity doctor;

}
