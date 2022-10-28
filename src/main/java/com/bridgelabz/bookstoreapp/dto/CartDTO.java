package com.bridgelabz.bookstoreapp.dto;

import com.bridgelabz.bookstoreapp.model.Book;
import com.bridgelabz.bookstoreapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private int userId;
    private int BookId;
    private int quantity;
}
