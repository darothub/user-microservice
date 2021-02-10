package com.darothub.usermicroservice.controllers;

import com.darothub.usermicroservice.exceptions.CustomException;
import com.darothub.usermicroservice.model.*;
import com.darothub.usermicroservice.model.jwt.JwtRequest;
import com.darothub.usermicroservice.model.jwt.JwtResponse;
import com.darothub.usermicroservice.model.response.ErrorResponse;
import com.darothub.usermicroservice.model.response.ResponseModel;
import com.darothub.usermicroservice.model.response.SuccessResponse;
import com.darothub.usermicroservice.services.UserService;
import com.darothub.usermicroservice.utils.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SuccessResponse successResponse;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    public UserController() {
    }
    @PostMapping("/artisans")
    public ResponseEntity<ResponseModel> addArtisan(@Valid @RequestBody UserEntity userEntity) {
        log.info("Inside ClientController add clientRequest" + userEntity);
        UserDTO artisanCreated = userService.addArtisan(userEntity);
        return handleSuccessResponseEntity("User added successfully", HttpStatus.CREATED, artisanCreated);
//        if(null != artisanCreated){
//
//        }
//        else{
//            return handleSuccessResponseEntity("User already exists", HttpStatus.CONFLICT, null);
//        }
//        log.info("Inside ClientController add clientRequest"+ client );


    }
    @GetMapping("/artisans/{id}")
    public ResponseEntity<ResponseModel> getUserById(@PathVariable Long id) {

        UserDTO artisanCreated = userService.getUserById(id);
//        log.info("Inside ClientController add clientRequest"+ client );
        return handleSuccessResponseEntity("User added successfully", HttpStatus.OK, artisanCreated);

    }

    @PostMapping("/login")
    public ResponseEntity<ResponseModel> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getEmailAddress(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid authentication credentials");
            throw new CustomException(error);
        }


        final UserDTO userDTO = userService.getUserByEmailAddress(jwtRequest.getEmailAddress());
        final UserDetails userDetails = userService.getUserByEmail(
                jwtRequest.getEmailAddress());


        if (userDetails.getPassword().equals(jwtRequest.getPassword())) {
            final String token = jwtUtility.generateToken(userDetails, userDTO);
            return handleSuccessResponseEntity("User found", HttpStatus.OK, new JwtResponse(token));
        } else {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }

    }
    @GetMapping("/")
    public String home() {

        return "Welcome";

    }
    public ResponseEntity<ResponseModel> handleSuccessResponseEntity(String message, HttpStatus status, Object payload) {
        successResponse.setMessage(message);
        successResponse.setStatus(status.value());
        successResponse.setPayload(payload);
        return ResponseEntity.ok(successResponse);
    }
}
