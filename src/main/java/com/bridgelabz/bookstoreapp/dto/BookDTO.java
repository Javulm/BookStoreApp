package com.bridgelabz.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @NotEmpty(message = "Book name cannot be empty")
    private String bookName;

    @NotEmpty(message = "Book Author cannot be empty")
    private String bookAuthor;

    @NotEmpty(message = "Book description cannot be empty")
    private String bookDescription;

    @NotEmpty(message = "Book logo cannot be empty")
    private String BookLogo;

    @NotNull(message = "Book price cannot be empty")
    private double bookPrice;

    @NotNull(message = "Book Quantity cannot be empty")
    private int bookQuantity;
}
