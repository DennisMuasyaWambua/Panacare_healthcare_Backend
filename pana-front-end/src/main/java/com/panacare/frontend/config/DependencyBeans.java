package com.panacare.frontend.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
//@Import({TemporalConfig.class})
@ComponentScan(basePackages = {"com.panacare.*"})
public class DependencyBeans {

}
