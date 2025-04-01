package com.panacare.frontend.controller;

import com.panacare.panabeans.workflow.service.OnboardingWorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/workflow/")
@Tag(name = "Panacare ProcessFlow Endpoints")
public class WorkflowController {
    private final OnboardingWorkflowService onboardingWorkflowService;

    public WorkflowController(OnboardingWorkflowService onboardingWorkflowService) {
        this.onboardingWorkflowService = onboardingWorkflowService;
    }

    @Operation(summary = "Validate doctor account")
    @PostMapping("validate/doctor/{doctorId}")
    public ResponseEntity<?> validateDoctorWorkflow(@PathVariable("doctorId")  String doctorId) {
        onboardingWorkflowService.validateDoctor(doctorId);
        return ResponseEntity.status(HttpStatus.OK).body("Doctor activation Process Initiated.");
    }

}
