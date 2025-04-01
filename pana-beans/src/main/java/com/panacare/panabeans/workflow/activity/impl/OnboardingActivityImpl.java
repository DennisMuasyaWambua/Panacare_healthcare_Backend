package com.panacare.panabeans.workflow.activity.impl;


import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.dto.UserDto;
import com.panacare.panabeans.service.UserService;
import com.panacare.panabeans.workflow.activity.OnboardingActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnboardingActivityImpl implements OnboardingActivity {

    private final UserService userService;



    @Override
    public void assignRolesAndSaveLocalUser(UserDto user) {
        userService.assignRolesAndSaveLocalUser(user);
    }

    @Override
    public void completeOnboarding(UserDto user) {
        userService.completeOnboarding(user);
    }

    @Override
    public void createClinic(String employeeId) {

    }

    @Override
    public void onboardDoctor(DoctorDto doctor) {
        userService.setDoctorVerified(doctor);
    }
}
