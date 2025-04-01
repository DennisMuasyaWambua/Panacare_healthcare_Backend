package com.panacare.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.panacare.frontend", "com.panacare.panabeans"})
public class PanaCareApplication {
    public static void main(String[] args) {
        SpringApplication.run(PanaCareApplication.class, args);
    }
}
