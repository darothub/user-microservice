package com.darothub.usermicroservice;

import com.darothub.usermicroservice.model.Artisan;
import com.darothub.usermicroservice.model.response.ErrorResponse;
import com.darothub.usermicroservice.model.response.SuccessResponse;
import com.darothub.usermicroservice.repositories.UserRepository;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class UserMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMicroServiceApplication.class, args);
    }

    @Bean
    public static SuccessResponse returnSuccessResponse(){
        return new SuccessResponse();
    }
    @Bean
    public static ErrorResponse returnErrorResponse(){
        return new ErrorResponse();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }



}
