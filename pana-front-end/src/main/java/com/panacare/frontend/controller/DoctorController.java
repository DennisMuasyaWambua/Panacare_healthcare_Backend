package com.panacare.frontend.controller;

import com.google.common.reflect.TypeToken;
import com.panacare.frontend.model.request.DoctorRequest;
import com.panacare.frontend.model.response.DoctorRest;
import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.service.DoctorService;
import com.panacare.panabeans.shared.SuccessMessages;
import com.panacare.panabeans.workflow.service.OnboardingWorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;

@RestController
@RequestMapping("/api/v1/doctor/")
@Tag(name = "Panacare Doctor Management Endpoints")
public class DoctorController {
    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    private final OnboardingWorkflowService onboardingWorkflowService;

    private static final Logger LOG = LoggerFactory.getLogger(DoctorController.class);

    public DoctorController(DoctorService doctorService, ModelMapper modelMapper, OnboardingWorkflowService onboardingWorkflowService) {
        this.doctorService = doctorService;
        this.modelMapper = modelMapper;
        this.onboardingWorkflowService = onboardingWorkflowService;
    }

    @Operation(summary = "Create a Doctor",
            description = "Create a Doctor In PanaCare. The response is the status and instructions on user activation.")
    @PostMapping(value = "create")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        DoctorDto doctorDto = modelMapper.map(doctorRequest, DoctorDto.class);
        doctorService.createDoctor(doctorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor created successfully!");
    }



    @Operation(summary = "Get doctors List on Panacare")
    @GetMapping("getDoctors")
    public ResponseEntity<Page<DoctorRest>> getDoctors(
            @RequestParam(value = "status", required = false) Boolean verified, Pageable pageable) {
        Page<DoctorDto> doctorDtos = doctorService.getDoctors(verified, pageable);
        Type listType = new TypeToken<Page<DoctorRest>>() {
        }.getType();
        Page<DoctorRest> doctors = modelMapper.map(doctorDtos, listType);
        return ResponseEntity.status(HttpStatus.OK).body(doctors);
    }

}
