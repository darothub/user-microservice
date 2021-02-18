package com.darothub.usermicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String category;
    private String thumbnail;
    private String emailAddress;
    private String phoneNumber;
    private String gender;
    private String country;


}
