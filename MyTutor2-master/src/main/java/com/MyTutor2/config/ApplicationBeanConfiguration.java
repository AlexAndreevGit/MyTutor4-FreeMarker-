package com.MyTutor2.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

//My project is a website where people can create an account and upload or search for tutoring offers.
//For the backend I use Spring Boot. I use MVC architecture. For the frontend I use HTML, CSS and FreeMarker.
//The Building Tool is Maven. The Database is MySQL.String
//
//´´´ ´´´     ´´´ ´´´


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    //SpringSecurity_3
    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();
    }

}
