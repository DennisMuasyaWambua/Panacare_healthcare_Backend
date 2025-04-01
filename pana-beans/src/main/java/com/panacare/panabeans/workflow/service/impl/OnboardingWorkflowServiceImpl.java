package com.panacare.panabeans.workflow.service.impl;


import com.panacare.panabeans.config.keycloakProvider;
import com.panacare.panabeans.data.entity.DoctorEntity;
import com.panacare.panabeans.data.repository.DoctorRepository;
import com.panacare.panabeans.data.repository.UserRepository;
import com.panacare.panabeans.workflow.service.OnboardingWorkflowService;
import com.panacare.panabeans.workflow.wf.OnboardingWorkFlow;
import io.temporal.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnboardingWorkflowServiceImpl implements OnboardingWorkflowService {

    private final WorkflowClient workflowClient;
    private final UserRepository userRepo;
    private final DoctorRepository doctorRepository;
    private final keycloakProvider keycloakProvider; // Injected keycloak Provider

    @Override
    public void activateAccount(String workflowId) {
        Keycloak keycloak = keycloakProvider.getInstance(); // Get keycloak instance
        String realm = keycloakProvider.realm; // Get realm from provider

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Get the specific user
        UserResource userResource = usersResource.get(workflowId);
        UserRepresentation user = userResource.toRepresentation();

        // Enable the user
        user.setEnabled(true);
        userResource.update(user);

        System.out.println("User " + workflowId + " has been approved and activated.");
        OnboardingWorkFlow onboardingWorkFlow = workflowClient
                .newWorkflowStub(OnboardingWorkFlow.class, workflowId);
        onboardingWorkFlow.signalCompleteOnBoarding();
    }

    @Override
    public void validateDoctor(String doctorId) {
        System.out.println("This is the doctor id: " + doctorId);
        Optional<DoctorEntity> doctor = doctorRepository.findByDoctorId(doctorId);
        System.out.println("This is the doctor: " + doctor);
//        doctor.ifPresentOrElse(doctorEntity -> {
//            OnboardingWorkFlow onboardingWorkFlow = workflowClient
//                    .newWorkflowStub(OnboardingWorkFlow.class, doctorEntity.getUser().getUserId());
//            onboardingWorkFlow.signalActivateDoctor();
//        }, () -> {
//            String errorMsg = String.format("Doctor account: %s", ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
//            throw new PanaAppException(errorMsg);
//        });
    }

    @Override
    public String getStatus(String workflowId) {
        OnboardingWorkFlow workflow = workflowClient.newWorkflowStub(OnboardingWorkFlow.class, workflowId);
        return workflow.getStatus();
    }
}
