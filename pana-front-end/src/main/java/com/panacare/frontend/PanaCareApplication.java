package com.panacare.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.panacare.frontend", "com.panacare.panabeans"})
@EnableAsync
public class PanaCareApplication {
    public static void main(String[] args) {
        SpringApplication.run(PanaCareApplication.class, args);
    }
}
