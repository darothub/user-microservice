package com.darothub.usermicroservice.services;

import com.darothub.usermicroservice.exceptions.CustomException;
import com.darothub.usermicroservice.model.dto.UploadImage;
import com.darothub.usermicroservice.model.dto.UserDTO;
import com.darothub.usermicroservice.model.Artisan;
import com.darothub.usermicroservice.model.response.ErrorResponse;
import com.darothub.usermicroservice.repositories.UploadRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper = new ModelMapper();
    String uploadsPath = "/Users/mac/Desktop/uploads";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadRepository uploadRepository;
    public UserDTO addArtisan(Artisan artisan) {

        log.info("Inside UserService add artisan" + artisan);
        Artisan oldUser = userRepository.findByEmailAddress(artisan.getEmailAddress());
        if(oldUser == null){
            Artisan userCreated = userRepository.save(artisan);

            UserDTO userDTO = modelMapper.map(userCreated, UserDTO.class);
            return userDTO;
        }
        else{
            ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.toString(), "User already exists");
            throw new CustomException(error);
        }

    }

    public void uploadToLocal(MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
        Path path = Paths.get(uploadsPath + file.getOriginalFilename());
        Files.write(path, data);

    }
    public UploadImage uploadToDb(MultipartFile file) throws IOException {
        UploadImage uploadImage = new UploadImage();
        uploadImage.setFileData(file.getBytes());
        uploadImage.setFileType(file.getContentType());
        uploadImage.setFileName(file.getOriginalFilename());
        return uploadRepository.save(uploadImage);

    }

    public UploadImage downloadImage(String imageId) throws IOException {
        return uploadRepository.getOne(imageId);
    }

    public UserDTO getUserByEmailAddress(String email) throws UsernameNotFoundException{
        try {
            Artisan artisan = userRepository.findByEmailAddress(email);
            UserDTO userDTO = modelMapper.map(artisan, UserDTO.class);
            return userDTO;

        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }

    public UserDTO getUserById(Long id) throws UsernameNotFoundException{
        try {
            Optional<Artisan> userEntity = userRepository.findById(id);
            Artisan getUser = userEntity.get();
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
            Optional<Artisan> userEntity = Optional.of(userRepository.findByEmailAddress(email));
            Artisan getUser = userEntity.get();
            return new User(getUser.getEmailAddress(), getUser.getPassword(), new ArrayList<>());
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }
    public UserDetails getUserByEmail(String email) throws UsernameNotFoundException {
        try {

            Optional<Artisan> userEntity = Optional.of(userRepository.findByEmailAddress(email));
            Artisan getUser = userEntity.get();
            return new User(getUser.getEmailAddress(), getUser.getPassword(), new ArrayList<>());
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        log.info("Id {}", id);
        try {
            Optional<Artisan> userEntity = userRepository.findById(id);
            Artisan getUser = userEntity.get();
            log.info("User {}", getUser);
            return new User(getUser.getEmailAddress(), getUser.getFirstName(), new ArrayList<>());
        } catch (Exception exception) {
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), "Invalid username/password");
            throw new CustomException(error);
        }
    }
}
