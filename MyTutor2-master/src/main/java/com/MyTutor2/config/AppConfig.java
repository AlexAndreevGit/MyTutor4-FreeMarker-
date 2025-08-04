package com.MyTutor2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//the @Configuration annotation indicates that this class can be used by the Spring IoC container as a source of bean definitions
@Configuration
public class AppConfig {

    //the @Bean annotation indicates that a method produces a bean to be managed by the Spring container
    //A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
