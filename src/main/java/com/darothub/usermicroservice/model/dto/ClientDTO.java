package com.darothub.usermicroservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String gender;
    private String deliveryAddress;
}
