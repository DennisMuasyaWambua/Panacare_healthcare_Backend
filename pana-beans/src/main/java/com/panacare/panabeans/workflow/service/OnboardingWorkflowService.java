package com.panacare.panabeans.workflow.service;


public interface OnboardingWorkflowService {

    void activateAccount(String workflowId);

    void validateDoctor(String userId);

    String getStatus(String workflowId);

}
