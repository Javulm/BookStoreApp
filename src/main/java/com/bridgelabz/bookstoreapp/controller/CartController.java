package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.Cart;
import com.bridgelabz.bookstoreapp.service.CartService;
import com.bridgelabz.bookstoreapp.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ICartService cartService;

    @PostMapping("/addtocart")
    public ResponseEntity<ResponseDTO> insertItem(@RequestBody CartDTO cartDTO) {
        String token = cartService.addToCart(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("item added to the cart", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getbytoken")
    public ResponseEntity<ResponseDTO> getCartDetailsByToken(@RequestHeader String token) {
        Optional<Cart> cart = cartService.getAllCartItemsForUser(token);
        ResponseDTO responseDTO = new ResponseDTO("Cart details retrieved successfully", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllCartDetails() {
        List<Cart> cart = cartService.getAllCartItems();
        ResponseDTO responseDTO = new ResponseDTO("All Cart details retrieved successfully", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateCartQuantity")
    public ResponseEntity<ResponseDTO> updateQuantity(@RequestHeader int cartId, @RequestHeader int quantity) {
        String cart = cartService.updateQuantity(cartId, quantity);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !", cart);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCartById(@RequestHeader int cartId) {
        String cart = cartService.removeFromCart(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart delete successfully", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
