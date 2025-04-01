package com.panacare.panabeans.service.impl;

import com.panacare.panabeans.data.entity.RoleEntity;
import com.panacare.panabeans.data.repository.RoleRepository;
import com.panacare.panabeans.dto.RoleDto;
import com.panacare.panabeans.exception.PanaAppException;
import com.panacare.panabeans.service.SetupService;
import com.panacare.panabeans.shared.PanaUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SetupServiceImpl implements SetupService {
    private final RoleRepository roleRepo;
    private final ModelMapper modelMapper;

    @Override
    public RoleDto getSingleRole(String roleId) {
        RoleEntity role = roleRepo.findByRoleId(roleId);
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public RoleDto updateRole(RoleDto role) throws PanaAppException {
        if (role.getName().isEmpty())
            throw new PanaAppException("Provide role name");
        RoleEntity oldRole = roleRepo.findByRoleId(role.getRoleId());
        if (oldRole == null)
            throw new PanaAppException("Provide role name");
        oldRole.setName(role.getName().toUpperCase());
        RoleEntity roleEntity = roleRepo.save(oldRole);
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        if (roleDto.getName().isEmpty())
            throw new PanaAppException("Provide role name");
        RoleEntity newRole = modelMapper.map(roleDto, RoleEntity.class);
        newRole.setRoleId(PanaUtils.generateID(10));
        RoleEntity roleEntity = roleRepo.save(newRole);
        return modelMapper.map(roleEntity, RoleDto.class);
    }

    @Override
    public Page<RoleDto> getRoleListing(String searchTerm, Pageable pageable) {
        Page<RoleEntity> rolePage;
        if(!StringUtils.isEmpty(searchTerm)) {
            rolePage = this.roleRepo
                    .findByNameContainsIgnoreCaseOrRoleIdContainsIgnoreCase(searchTerm, searchTerm, pageable);
        }else{
            rolePage = this.roleRepo.findAll(pageable);
        }
        if(rolePage == null)
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        Type listType = new TypeToken<List<RoleDto>>() {}.getType();
        List<RoleDto> targets = modelMapper.map(rolePage.getContent(), listType);
        return new PageImpl<>(targets, pageable, rolePage.getTotalElements());
    }


}
