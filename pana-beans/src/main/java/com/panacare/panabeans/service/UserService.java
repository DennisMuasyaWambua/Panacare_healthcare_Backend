package com.panacare.panabeans.service;

import com.panacare.panabeans.dto.DoctorDto;
import com.panacare.panabeans.dto.UserDto;


public interface UserService  {
  void startOnboardingWorkflow(UserDto user);
  void assignRolesAndSaveLocalUser(UserDto user);

  void completeOnboarding(UserDto user);

  void setDoctorVerified(DoctorDto doctor);
}
