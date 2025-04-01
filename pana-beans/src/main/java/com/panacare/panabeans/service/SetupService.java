package com.panacare.panabeans.service;

import com.panacare.panabeans.dto.RoleDto;
import com.panacare.panabeans.exception.PanaAppException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SetupService {

  RoleDto getSingleRole(String roleId);

  Page<RoleDto> getRoleListing(String searchTerm, Pageable pageable);

  RoleDto updateRole(RoleDto role) throws PanaAppException;

  RoleDto createRole(RoleDto roleDto);
}
