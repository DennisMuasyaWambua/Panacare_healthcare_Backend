package com.panacare.panabeans.workflow.wf;

import com.panacare.panabeans.dto.UserDto;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OnboardingWorkFlow {
    @WorkflowMethod
    void startUserOnboarding(UserDto user);

    @SignalMethod
    void signalCompleteOnBoarding();

    @SignalMethod
    void signalActivateDoctor();

    @QueryMethod
    String getStatus();
}
