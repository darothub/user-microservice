package com.darothub.usermicroservice.controllers;

import com.darothub.usermicroservice.exceptions.CustomException;
import com.darothub.usermicroservice.model.*;
import com.darothub.usermicroservice.model.dto.ImageUploadDTO;
import com.darothub.usermicroservice.model.dto.UploadImage;
import com.darothub.usermicroservice.model.dto.UserDTO;
import com.darothub.usermicroservice.model.jwt.JwtRequest;
import com.darothub.usermicroservice.model.jwt.JwtResponse;
import com.darothub.usermicroservice.model.response.ErrorResponse;
import com.darothub.usermicroservice.model.response.ResponseModel;
import com.darothub.usermicroservice.model.response.SuccessResponse;
import com.darothub.usermicroservice.services.UserService;
import com.darothub.usermicroservice.utils.ConstantUtils;
import com.darothub.usermicroservice.utils.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;

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
    public ResponseEntity<ResponseModel> addArtisan(@Valid @RequestBody Artisan artisan) {
        log.info("Inside ClientController add clientRequest" + artisan);
        UserDTO artisanCreated = userService.addArtisan(artisan);
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

    @PostMapping("/upload/local")
    public void uploadToLocal(@RequestParam("file") MultipartFile file) throws IOException {
        userService.uploadToLocal(file);
    }

    @PostMapping("/upload/db")
    public ResponseEntity<ResponseModel> uploadToDb(@RequestParam("file") MultipartFile file) throws IOException {
        UploadImage uploadImage = userService.uploadToDb(file);
        ImageUploadDTO imageUploadDTO = new ImageUploadDTO();
        if(uploadImage != null && uploadImage.getFileType().matches(ConstantUtils.IMAGE_PATTERN)){
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(uploadImage.getFileId())
                    .toUriString();
            imageUploadDTO.setDownloadUri(uri);
            imageUploadDTO.setFileId(uploadImage.getFileId());
            imageUploadDTO.setFileName(uploadImage.getFileName());
            imageUploadDTO.setFileType(uploadImage.getFileType());
            imageUploadDTO.setUploadStatus(true);
            return handleSuccessResponseEntity("Image uploaded successfully", HttpStatus.OK, imageUploadDTO);
        }
        return handleSuccessResponseEntity("Invalid image format", HttpStatus.EXPECTATION_FAILED, null);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) throws IOException {
        UploadImage uploadImage = userService.downloadImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(uploadImage.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename"+uploadImage.getFileName())
                .body(new ByteArrayResource(uploadImage.getFileData()));

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
        return new ResponseEntity<>(successResponse, status);
    }
}
