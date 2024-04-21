package com.bdd_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
public class PersonneDTO implements Serializable{
    private Long id;
    @NotEmpty(message = "firstname is empty")
    @NotBlank(message = "firstname is blanck")
    private String firstName;
    @NotEmpty(message = "last is empty")
    @NotBlank(message = "lastname is empty")
    private String lastName;
    @Email(message = "email not conformed")
    @NotEmpty(message = "email is empty")
    @NotBlank(message = "email is blanck")
    private String email;
    @NotEmpty(message = "last is empty")
    @NotBlank(message = "lastname is empty")
    @Min(message = "minimum 9 characters", value = 9L)
    private String phoneNumber;
    private String date;
}
