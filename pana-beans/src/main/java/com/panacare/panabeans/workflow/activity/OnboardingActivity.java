package com.panacare.panabeans.workflow.activity;

import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.dto.UserDto;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OnboardingActivity {

    @ActivityMethod
    void assignRolesAndSaveLocalUser(UserDto user);

    @ActivityMethod
    void completeOnboarding(UserDto user);

    @ActivityMethod
    void createClinic(String employeeId);
    @ActivityMethod
    void onboardDoctor(DoctorDto user);
}
