package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s?]{2,}$", message = "invalid firstname! should start with uppercase letter")
    @NotEmpty(message = "First name cannot be null")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-zA-Z\\s?]{2,}$", message = "invalid lastname! should start with uppercase letter")
    @NotEmpty(message = "Last name cannot be null")
    private String lastName;

    @Email(message = "Invalid Email")
    private String email;

    @Pattern(regexp = "^([0-9]{2,}\\s)?[0-9]{10}$", message = "Phone number is invalid")
    @NotEmpty(message = "Phone number cannot be null")
    private String number;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#@$!%*?&])[A-Za-z\\d#@$!%*?&]{8,}$", message = "password must contain at least one Uppercase letter,one number and a special character")
    @NotEmpty(message = "Password cannot be null")
    private String password;
}
