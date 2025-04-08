//package com.panacare.panabeans.config;
//
//import com.google.protobuf.util.Durations;
//import io.temporal.api.workflowservice.v1.RegisterNamespaceRequest;
//import io.temporal.api.workflowservice.v1.RegisterNamespaceResponse;
//import io.temporal.client.WorkflowClient;
//import io.temporal.client.WorkflowClientOptions;
//import io.temporal.serviceclient.WorkflowServiceStubs;
//import io.temporal.serviceclient.WorkflowServiceStubsOptions;
//import io.temporal.worker.WorkerFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@RequiredArgsConstructor
//public class TemporalConfig {
//
//    @Value("${temporal-service-address}")
//    private  String temporalServiceAddress;
//
//    @Value("${temporal-name-space}")
//    private String temporalNameSpace;
//
//    public TemporalConfig(String temporalServiceAddress, String temporalNameSpace) {
//        this.temporalServiceAddress = temporalServiceAddress;
//        this.temporalNameSpace = temporalNameSpace;
//    }
//
//
//    @Bean
//    public WorkflowServiceStubs workflowServiceStubs() {
//        return WorkflowServiceStubs
//                .newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
//                        .setTarget(temporalServiceAddress).build());
//    }
//
//    @Bean
//    public RegisterNamespaceResponse registerNamespaceRequest(WorkflowServiceStubs workflowServiceStubs) {
//        RegisterNamespaceResponse response = null;
//        RegisterNamespaceRequest req = RegisterNamespaceRequest.newBuilder()
//                .setNamespace(temporalNameSpace)
//                .setWorkflowExecutionRetentionPeriod(Durations.fromDays(30)) // keeps the Workflow Execution
//                //Event History for up to 3 days in the Persistence store. Not setting this value will throw an error.
//                .build();
//        try{
//            response = workflowServiceStubs.blockingStub().registerNamespace(req);
//        }catch (Exception e){
////            e.printStackTrace();
//        }
//        return response;
//    }
//
//    @Bean
//    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs){
//        return WorkflowClient.newInstance(workflowServiceStubs,
//                WorkflowClientOptions.newBuilder().setNamespace(temporalNameSpace).build());
//    }
//
//    @Bean
//    public WorkerFactory workerFactory(WorkflowClient workflowClient) {
//        return WorkerFactory.newInstance(workflowClient);
//    }
//
//}
