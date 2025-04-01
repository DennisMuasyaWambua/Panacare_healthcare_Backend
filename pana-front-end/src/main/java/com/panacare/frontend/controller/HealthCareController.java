package com.panacare.frontend.controller;

import com.google.common.reflect.TypeToken;
import com.panacare.frontend.model.request.HealthCareRequest;
import com.panacare.frontend.model.response.HealthCareRest;
import com.panacare.panabeans.dto.HealthCareDto;
import com.panacare.panabeans.service.HealthCareService;
import com.panacare.panabeans.shared.HealthCareCategories;
import com.panacare.panabeans.shared.SuccessMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/healthcare/")
@Tag(name = "Panacare HealthCare Management Endpoints")
public class HealthCareController {
    private final HealthCareService healthCareService;
    private final ModelMapper modelMapper;

    private static final Logger LOG = LoggerFactory.getLogger(HealthCareController.class);

    public HealthCareController(HealthCareService healthCareService, ModelMapper modelMapper) {
        this.healthCareService = healthCareService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a HealthCare Facility",
            description = "Create a Doctor In PanaCare. The response is the status and instructions on user activation.")
    @PostMapping(value = "create")
    public ResponseEntity<?> createHealthCare(@RequestBody HealthCareRequest healthCareRequest) {
        HealthCareDto healthCareDto = modelMapper.map(healthCareRequest, HealthCareDto.class);
        healthCareService.createHealthCare(healthCareDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Health center created successfully!");
    }


    @Operation(summary = "Get Healthcare List on Panacare")
    @GetMapping("getHealthCare")
    public ResponseEntity<Page<HealthCareRest>> getDoctors(
            @RequestParam(value = "category", required = false) HealthCareCategories category, Pageable pageable) {
        Page<HealthCareDto> healthCareDtos = healthCareService.getHealthCareFacilities(category,  pageable);
        Type listType = new TypeToken<Page<HealthCareRest>>() {
        }.getType();
        Page<HealthCareRest> healthCareRests = modelMapper.map(healthCareDtos, listType);
        return ResponseEntity.status(HttpStatus.OK).body(healthCareRests);
    }

}
