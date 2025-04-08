package com.panacare.panabeans.service.impl;

import com.panacare.panabeans.communication.EmailService;
import com.panacare.panabeans.config.keycloakProvider;
import com.panacare.panabeans.data.entity.RoleEntity;
import com.panacare.panabeans.data.entity.UserEntity;
import com.panacare.panabeans.data.repository.DoctorRepository;
import com.panacare.panabeans.data.repository.RoleRepository;
import com.panacare.panabeans.data.repository.UserRepository;
import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.dto.UserDto;
import com.panacare.panabeans.exception.PanaAppException;
import com.panacare.panabeans.mail.CustomEmailService;
import com.panacare.panabeans.service.UserService;
import com.panacare.panabeans.shared.ErrorMessages;
import com.panacare.panabeans.shared.PanaUtils;
import com.panacare.panabeans.workflow.utilities.WorkflowUtilities;
import com.panacare.panabeans.workflow.wf.OnboardingWorkFlow;
import io.temporal.client.WorkflowClient;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.resource}")
    public String clientID;
    @Value("${app.base.url}")
    public String baseUrl;
    @Value("${app.frontBase.url}")
    public String frontEndBaseUrl;

    @Value("${app.api.version}")
    public String apiVersion;
    private final keycloakProvider kcProvider;
    private final UserRepository userRepo;
    private final DoctorRepository doctorRepo;
    private final RoleRepository roleRepo;
    private final PanaUtils utils;
    private final CustomEmailService customEmailService;
    private final EmailService emailService2;

//    private final WorkflowUtilities workflowUtilities;

    @Override
    public void startOnboardingWorkflow(UserDto user) {
        if (userRepo.findByEmailIgnoreCase(user.getEmail()) != null) {
            throw new PanaAppException("User " + ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }
        String userId = createkeycloakUser(user);
        logger.info("Created keycloak user with userId: {}", userId);
        user.setUserId(userId);
        //todo save user details to the user database
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setTitle(user.getTitle());
        userEntity.setCountry(user.getCountry());
        userEntity.setImageUrl(user.getImageUrl());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setAccountActivated(Boolean.FALSE);
        userEntity.setRoles(new HashSet<>());
        userRepo.save(userEntity);
        //todo send activation email to the user with the user id
        String subject = "Account Activation";
        String activationUrl = "https://" + frontEndBaseUrl + "/api/v1/user/onboard/" + userId;

        String body = "<html><body>"
                + "Hello,<br><br>"
                + "Click <a href=\"" + activationUrl + "\">here</a> to activate your account.<br><br>"
                + "If the link doesn't work, copy and paste the following URL into your browser:<br>"
                + "<a href=\"" + activationUrl + "\">" + activationUrl + "</a><br><br>"
                + "</body></html>";

        customEmailService.sendEmail(user.getEmail(), subject, body, true); // 'true' indicates HTML content

//        OnboardingWorkFlow workFlow = workflowUtilities.createOnboardingWorkflowConnection(userId);
//        WorkflowClient.start(workFlow::startUserOnboarding, user);
    }

    @Override
    public void assignRolesAndSaveLocalUser(UserDto user) {
        //Get User Id
        String userId = user.getUserId();

        // Get realm
        RealmResource realmResource = kcProvider.getInstance().realm(realm);
        UsersResource usersResource = realmResource.users();

        UserResource userResource = usersResource.get(userId);

        // Define password credential
        CredentialRepresentation passwordCred = createPasswordCredentials("tempPassword");

        // Set password credential
        userResource.resetPassword(passwordCred);

        // Get realm role "user" (requires view-realm role)
        RoleRepresentation userRealmRole = realmResource.roles().get("user").toRepresentation();

        //        // Assign realm role tester to user
        List<RoleRepresentation> rolesToAdd = Collections.singletonList(userRealmRole);

        userResource.roles().realmLevel().add(rolesToAdd);

        //        // Get client
        ClientRepresentation appClient = realmResource.clients() //
                .findByClientId(clientID).get(0);
//
//      Initialize client level role to user
        List<RoleRepresentation> clientRoles = new ArrayList<>();

        //  Get System roles
        Set<RoleEntity> roleEntities = new HashSet<>();
        user.getRoles().forEach(s -> {
            String roleName = s.getName();
            RoleEntity role = roleRepo.findByName(roleName);
//           Get client level role (requires view-clients role)
            RoleRepresentation userClientRole = realmResource.clients()
                    .get(appClient.getId()).roles().get(roleName).toRepresentation();
            clientRoles.add(userClientRole);
            roleEntities.add(role);
        });

//      Assign client level role to user
        userResource.roles().clientLevel(appClient.getId()).add(clientRoles);

        //If you want to save the user to your other database, do it here:
        UserEntity localUser = UserEntity.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .title(user.getTitle())
                .country(user.getCountry())
                .imageUrl(user.getImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .accountActivated(Boolean.FALSE)
                .roles(roleEntities)
                .build();

        if (isDoctorAccount(user)) {
            String verificationToken = utils.generateEmailVerificationToken(user.getUserId());
            localUser.setVerificationToken(verificationToken);
        }

        // Send password reset E-Mail
        // VERIFY_EMAIL, UPDATE_PROFILE, CONFIGURE_TOTP, UPDATE_PASSWORD, TERMS_AND_CONDITIONS
        List<String> requiredActions = new ArrayList<>();
        requiredActions.add("UPDATE_PASSWORD");
        requiredActions.add("CONFIGURE_TOTP");
        requiredActions.add("VERIFY_EMAIL");
        String redirectUri = String.format("%s/api/%s/user/onboard/%s",baseUrl, apiVersion, userId);
        userResource.executeActionsEmail(clientID, redirectUri, requiredActions);
        userRepo.save(localUser);
    }

    @Override
    public void completeOnboarding(UserDto user) {
        String userId = user.getUserId();
        userRepo.findByUserId(userId).ifPresentOrElse(userEntity -> {
            userEntity.setAccountActivated(Boolean.TRUE);
            userEntity.setDateAccountActivated(new Date());
            userRepo.save(userEntity);
        }, () -> {
            String errorMsg =String.format("User: %s %s", userId,ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
            throw new PanaAppException(errorMsg);
        });
    }

    @Override
    public void setDoctorVerified(DoctorDto doctor) {
        String doctorId = doctor.getDoctorId();
        doctorRepo.findByDoctorId(doctorId).ifPresentOrElse(doctorEntity -> {
            doctorEntity.setVerified(Boolean.TRUE);
            doctorEntity.setDateVerified(new Date());
            doctorRepo.save(doctorEntity);
        }, () -> {
            String errorMsg = String.format("Doctor: %s %s", doctorId, ErrorMessages.RECORD_NOT_FOUND.getErrorMessage());
            throw new PanaAppException(errorMsg);
        });
    }

    private String createkeycloakUser(UserDto user) {
        // Get realm
        RealmResource realmResource = kcProvider.getInstance().realm(realm);
        UsersResource usersResource = realmResource.users();

        // Check if the user already exists
        List<UserRepresentation> existingUsers = usersResource.search(user.getEmail(), 0, 1);
        System.out.println("This is the list of existing users: "+ existingUsers);
        if (!existingUsers.isEmpty()) {
            logger.warn("User with email {} already exists in keycloak.", user.getEmail());
            throw new PanaAppException("User with email " + user.getEmail() + " already exists.");
        }

        // Create new user
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("title", Collections.singletonList(user.getTitle()));
        attributes.put("country", Collections.singletonList(user.getCountry()));
        attributes.put("imageUrl", Collections.singletonList(user.getImageUrl()));
        attributes.put("phoneNumber", Collections.singletonList(user.getPhoneNumber()));
        kcUser.setAttributes(attributes);

        Response response = usersResource.create(kcUser);
        logger.info("Response status: {}", response.getStatus());
        logger.info("Response body: {}", response.getStatusInfo());

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            logger.info("Created password: {}", user.getPassword());
            logger.info("User created successfully with userId: {}", userId);


            /// Set user password
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(user.getPassword());
            passwordCred.setTemporary(false);  // If true, forces user to reset password

            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCred);  // Use resetPassword instead of updateCredential

            logger.info("Password set for user: {}", user.getEmail());

            return userId;
        } else {
            logger.error("Failed to create user: {} with response error: {}", user.getUsername(), response);
            throw new PanaAppException("Failed to create user with email " + user.getEmail());
        }
    }



    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    public static boolean isDoctorAccount(UserDto userDto) {
        return userDto.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("doctor"));
    }


}
