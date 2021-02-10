package com.darothub.usermicroservice;

import com.darothub.usermicroservice.model.response.ErrorResponse;
import com.darothub.usermicroservice.model.response.SuccessResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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


//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//        };
//    }

}
