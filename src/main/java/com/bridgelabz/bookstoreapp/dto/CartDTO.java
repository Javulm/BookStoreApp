package com.bridgelabz.bookstoreapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Integer userId;
    private Integer BookId;
    private Integer quantity;
}
