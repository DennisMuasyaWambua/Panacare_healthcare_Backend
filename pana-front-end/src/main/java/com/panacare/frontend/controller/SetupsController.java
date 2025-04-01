package com.panacare.frontend.controller;

import com.panacare.frontend.model.request.RoleRequestModel;
import com.panacare.frontend.model.response.CustomResponse;
import com.panacare.frontend.model.response.RoleRest;
import com.panacare.panabeans.dto.RoleDto;
import com.panacare.panabeans.service.SetupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/api/v1/settings/")
@Tag(name = "Panacare Setups Management Endpoints")
public class SetupsController {
    private final SetupService setupService;

    private final ModelMapper modelMapper;

    private static final Logger LOG = LoggerFactory.getLogger(SetupsController.class);

    public SetupsController(SetupService setupService, ModelMapper modelMapper) {
        this.setupService = setupService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get All Roles defined in PanaCare Application")
    @GetMapping(value = {"roles/listing"})
    public ResponseEntity<?> displayRolesList(@RequestParam(required = false) String searchTerm, Pageable pageable) {
        Page<RoleDto> roleListing = setupService.getRoleListing(searchTerm, pageable);
        Type listType = new TypeToken<List<RoleRest>>() {}.getType();
        List<RoleRest> targets = modelMapper.map(roleListing.getContent(), listType);
        Page<RoleRest> rolePage =new PageImpl<>(targets, pageable, roleListing.getTotalElements());
        return new ResponseEntity<>(rolePage, HttpStatus.OK);
    }

    @Operation(summary = "Fetch Role Details in PanaCare using the roleId")
    @GetMapping(value = {"roles/{roleId}"})
    public ResponseEntity<?> getSingleRole(@PathVariable("roleId") String roleId) {
        CustomResponse response = new CustomResponse();
        try {
            response.setBody(setupService.getSingleRole(roleId));
            response.setStatusCode("200");
            response.setStatusDescription("created");
        } catch (Exception ex) {
            response.setBody("error");
            response.setStatusCode("500");
            response.setStatusDescription(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Create a new Role in PanaCare Application")
    @PostMapping(value = {"roles"})
    public ResponseEntity<?> createRole(@RequestBody RoleRequestModel role) {
        RoleDto roleDto =  modelMapper.map(role, RoleDto.class);
        CustomResponse response = new CustomResponse();
        try {
            RoleDto createdDto = setupService.createRole(roleDto);
            RoleRest rolerest =  modelMapper.map(createdDto, RoleRest.class);
            response.setBody(rolerest);
            response.setStatusCode("200");
            response.setStatusDescription("created");

        } catch (Exception ex) {
            response.setBody("error");
            response.setStatusCode("500");
            response.setStatusDescription(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Edit existing Role in PanaCare Application")
    @PutMapping(value = {"roles/{roleId}"})
    public ResponseEntity<?> editRole(@RequestBody RoleRequestModel role) {
        RoleDto roleDto =  modelMapper.map(role, RoleDto.class);;
        CustomResponse response = new CustomResponse();
        try {
            response.setBody(setupService.updateRole(roleDto));
            response.setStatusCode("200");
            response.setStatusDescription("created");
        } catch (Exception ex) {
            response.setBody("error");
            response.setStatusCode("500");
            response.setStatusDescription(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
