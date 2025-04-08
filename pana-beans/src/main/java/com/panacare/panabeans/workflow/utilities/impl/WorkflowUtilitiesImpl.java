//package com.panacare.panabeans.workflow.utilities.impl;
//
//import com.panacare.panabeans.shared.PanaCareQueue;
//import com.panacare.panabeans.workflow.utilities.WorkflowUtilities;
//import com.panacare.panabeans.workflow.wf.OnboardingWorkFlow;
//import io.temporal.client.WorkflowClient;
//import io.temporal.client.WorkflowOptions;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class WorkflowUtilitiesImpl implements WorkflowUtilities {
//
//    private final WorkflowClient workflowClient;
//
//
//    @Override
//    public OnboardingWorkFlow createOnboardingWorkflowConnection(String workflowId) {
//        WorkflowOptions options = WorkflowOptions
//                .newBuilder()
//                .setTaskQueue(PanaCareQueue.ONBOARDING_WORKFLOW_TASK_QUEUE.name())
//                .setWorkflowId(workflowId)
//                .build();
//        return workflowClient.newWorkflowStub(OnboardingWorkFlow.class, options);
//    }
//}
