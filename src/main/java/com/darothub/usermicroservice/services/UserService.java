package com.darothub.usermicroservice.services;

import com.darothub.usermicroservice.exceptions.CustomException;
import com.darothub.usermicroservice.model.UserDTO;
import com.darothub.usermicroservice.model.UserEntity;
import com.darothub.usermicroservice.model.response.ErrorResponse;
import com.darothub.usermicroservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;
    public UserDTO addArtisan(UserEntity userEntity) {

        log.info("Inside UserService add artisan" + userEntity);
        UserEntity oldUser = userRepository.findByEmailAddress(userEntity.getEmailAddress());
        if(oldUser == null){
            UserEntity userCreated = userRepository.save(userEntity);

            UserDTO userDTO = modelMapper.map(userCreated, UserDTO.class);
            return userDTO;
        }
        else{
            ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.toString(), "User already exists");
            throw new CustomException(error);
        }

    }

    public UserDTO getUserByEmailAddress(String email) throws UsernameNotFoundException{
        try {
            UserEntity userEntity = userRepository.findByEmailAddress(email);
            UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
            return userDTO;

        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }

    public UserDTO getUserById(Long id) throws UsernameNotFoundException{
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            UserEntity getUser = userEntity.get();
            UserDTO userDTO = modelMapper.map(getUser, UserDTO.class);
            return userDTO;

        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Email {}", email);
        try {
            Optional<UserEntity> userEntity = Optional.of(userRepository.findByEmailAddress(email));
            UserEntity getUser = userEntity.get();
            return new User(getUser.getEmailAddress(), getUser.getPassword(), new ArrayList<>());
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }
    public UserDetails getUserByEmail(String email) throws UsernameNotFoundException {
        try {

            Optional<UserEntity> userEntity = Optional.of(userRepository.findByEmailAddress(email));
            UserEntity getUser = userEntity.get();
            return new User(getUser.getEmailAddress(), getUser.getPassword(), new ArrayList<>());
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        log.info("Id {}", id);
        try {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            UserEntity getUser = userEntity.get();
            log.info("User {}", getUser);
            return new User(getUser.getEmailAddress(), getUser.getPassword(), new ArrayList<>());
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }
}
