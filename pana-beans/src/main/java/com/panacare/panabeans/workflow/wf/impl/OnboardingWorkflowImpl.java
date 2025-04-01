package com.panacare.panabeans.workflow.wf.impl;


import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.dto.UserDto;
import com.panacare.panabeans.workflow.activity.OnboardingActivity;
import com.panacare.panabeans.workflow.wf.OnboardingWorkFlow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

import static com.panacare.panabeans.service.impl.UserServiceImpl.isDoctorAccount;

@Slf4j
public class OnboardingWorkflowImpl implements OnboardingWorkFlow {

    public final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(50000)
            .build();

    public final ActivityOptions options = ActivityOptions.newBuilder()
//            .setScheduleToCloseTimeout(Duration.ofMinutes(1))
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryOptions)
            .build();

    public final OnboardingActivity activity = Workflow.newActivityStub(OnboardingActivity.class, options);

    private String status;

    private String userId= "";

    private boolean isDoctorAccount = false;
    private boolean isDoctorVerified = false;
    private boolean isUserAccountActivated = false;


    @Override
    public void startUserOnboarding(UserDto user) {
        this.userId = user.getUserId();
        log.info("**************Staring user Onboarding Workflow for {}", this.userId);
        this.isDoctorAccount = isDoctorAccount(user);
        log.info("**************Is Doctor Account: {}", this.isDoctorAccount);
        status = "Started Role assignment";
        log.info("**************Assigning roles and saving user to database!**************");
        activity.assignRolesAndSaveLocalUser(user);
        status = "Waiting for user to verify email and activate account!";
        log.info("**************" + status);
        Workflow.await(() -> isUserAccountActivated);
        if(this.isDoctorAccount){
            status = "Waiting for doctor account to be verified";
            log.info("**************Waiting for Doctor Account: {} to be verified", this.userId);
            Workflow.await(() -> isDoctorVerified);
            DoctorDto doctorDto = user.getDoctorDto();
            activity.onboardDoctor(doctorDto);
        }
        activity.completeOnboarding(user);
        log.info("**************Notifying employee user on successful onboarding!");
        status = "Onboarding Completed";
    }

    @Override
    public void signalCompleteOnBoarding() {
        this.isUserAccountActivated= true;
        log.info("**************User: {} successfully verified the account!", this.userId);
    }


    @Override
    public void signalActivateDoctor() {
        this.isDoctorVerified= true;
        log.info("**************Doctor Account: {} successfully verified !", this.userId);
    }

    @Override
    public String getStatus() {
        return status;
    }

}
