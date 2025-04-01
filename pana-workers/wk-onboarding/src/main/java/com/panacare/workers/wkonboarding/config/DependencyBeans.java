package com.panacare.workers.wkonboarding.config;

import com.panacare.panabeans.config.TemporalConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TemporalConfig.class})
@ComponentScan(basePackages = {"com.panacare.*"})
public class DependencyBeans {

}
