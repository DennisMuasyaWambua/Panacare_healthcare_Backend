package com.panacare.frontend.controller;

import com.panacare.frontend.model.request.LoginRequest;
import com.panacare.frontend.model.request.UserRequest;
import com.panacare.panabeans.config.keycloakProvider;
import com.panacare.panabeans.dto.UserDto;
import com.panacare.panabeans.service.UserService;
import com.panacare.panabeans.shared.SuccessMessages;
import com.panacare.panabeans.workflow.service.OnboardingWorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/")
@Tag(name = "Panacare User Management Endpoints")
public class UserController {
    private final UserService userService;

    private final keycloakProvider kcProvider;

    private final ModelMapper modelMapper;

    private final OnboardingWorkflowService onboardingWorkflowService;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, keycloakProvider kcProvider, ModelMapper modelMapper, OnboardingWorkflowService onboardingWorkflowService) {
        this.userService = userService;
        this.kcProvider = kcProvider;
        this.modelMapper = modelMapper;
        this.onboardingWorkflowService = onboardingWorkflowService;
    }

    @Operation(summary = "Create a User",
            description = "Create a User In PanaCare. The response is the status and instructions on user activation.")
    @PostMapping(value = "user/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
        System.out.print("THis is the user password created: " + user.getPassword());
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.startOnboardingWorkflow(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessMessages.SUCCESS_USER_CREATED.getSuccessMessage());
    }

    @Operation(summary = "User login")
    @PostMapping("user/login")
    public ResponseEntity<AccessTokenResponse> login(@NotNull @RequestBody LoginRequest loginRequest) {

        Keycloak keycloak = kcProvider
                .newkeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword())
                .build();
        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (Exception ex) {
            LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }

    @Operation(summary = "Activate User account on Panacare")
    @GetMapping("user/onboard/{userId}")
    public ResponseEntity<String> confirmAccountWorkflow(@PathVariable String userId) {
        onboardingWorkflowService.activateAccount(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User activation Process Initiated.");
    }

}
