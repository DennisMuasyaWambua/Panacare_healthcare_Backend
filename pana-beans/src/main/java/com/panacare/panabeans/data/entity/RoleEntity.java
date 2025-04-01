package com.panacare.panabeans.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tbl_framework_role")
public class RoleEntity extends StandardEntity {

    @Column(length = 10, nullable = false)
    private String roleId;

    private String name;
}

