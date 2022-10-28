package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.model.Cart;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    String addToCart(CartDTO cartDTO);
    String removeFromCart(int cartId);
    String updateQuantity(int cartId, int quantity);
    Optional<Cart> getAllCartItemsForUser(String token);
    List<Cart> getAllCartItems();
}
