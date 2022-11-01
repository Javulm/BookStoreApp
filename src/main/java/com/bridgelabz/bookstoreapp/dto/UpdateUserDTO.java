package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UpdateUserDTO {
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s?]{2,}$", message = "invalid firstname! should start with uppercase letter")
    @NotEmpty(message = "First name cannot be null")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-zA-Z\\s?]{2,}$", message = "invalid lastname! should start with uppercase letter")
    @NotEmpty(message = "Last name cannot be null")
    private String lastName;

    @Pattern(regexp = "^([0-9]{2,}\\s)?[0-9]{10}$", message = "Phone number is invalid")
    @NotEmpty(message = "Phone number cannot be null")
    private String number;

}
