package com.panacare.workers.wkonboarding;

import com.panacare.panabeans.shared.PanaCareQueue;
import com.panacare.panabeans.workflow.activity.OnboardingActivity;
import com.panacare.panabeans.workflow.wf.impl.OnboardingWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class OnboardWorkerApplication {
    public static void main(String[] args) {
        ApplicationContext appContext = SpringApplication.run(OnboardWorkerApplication.class, args);

        WorkerFactory factory = appContext.getBean(WorkerFactory.class);

        OnboardingActivity onboardingActivity = appContext.getBean(OnboardingActivity.class);

        // Worker factory is used to create Workers that poll specific Task Queues.
        Worker onboardWorker = factory.newWorker(PanaCareQueue.ONBOARDING_WORKFLOW_TASK_QUEUE.name());
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful so a type is needed to create instances.
        onboardWorker.registerWorkflowImplementationTypes(OnboardingWorkflowImpl.class);
        // Activities are stateless and thread safe so a shared instance can be used.
        onboardWorker.registerActivitiesImplementations(onboardingActivity);

        // Start listening to the Task Queue.
        factory.start();
        log.info("Registered Panacare Onboarding worker..");

    }
}
