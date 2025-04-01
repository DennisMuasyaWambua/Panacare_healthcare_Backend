package com.panacare.panabeans.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GenericBeans {

    @Bean
    public RestTemplate simpleRestTemplate(RestTemplateBuilder restTemplateBuilder,
                                           RestTemplateCustomizer restTemplateCustomizer) {
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .additionalCustomizers(restTemplateCustomizer)
                .messageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Bean
    public RestTemplateCustomizer restTemplateCustomizer(LogRestRequestInterceptor logRestRequestInterceptor) {
        return restTemplate -> restTemplate.getInterceptors().add(logRestRequestInterceptor);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
