package com.darothub.usermicroservice.model;

import com.darothub.usermicroservice.utils.ConstantUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Artisan {

    @Id
    @SequenceGenerator(name = "artisan_seq", sequenceName = "artisan_seq", initialValue = 1, allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="artisan_seq")
    private Long id;
    @NotNull
    @NotBlank
    @Pattern(regexp = ConstantUtils.CHAR_PATTERN, message = "Please enter characters only")
    private String firstName;
    @Size(min = 3, max = 46)
    @NotNull
    private String lastName;
    @NotNull
    @NotBlank
    @Pattern(regexp = ConstantUtils.CATEGORY_PATTERN, message = "Invalid category")
    private String category;
    @NotNull
    private String password;
    private String thumbnail;
    @Email
    @NotBlank
    @NotNull
    private String emailAddress;
    private String phoneNumber;
    @Pattern(regexp = ConstantUtils.GENDER_PATTERN, message = "Invalid gender type")
    private String gender;
    private String country;
}
