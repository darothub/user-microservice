package com.darothub.usermicroservice.model.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    @JsonProperty("email_address")
    private String emailAddress;
    private String password;
}
