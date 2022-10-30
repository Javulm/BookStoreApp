package com.bridgelabz.bookstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue
    private int cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;
    private double totalPrice;

    public Cart(User user, Book book, int quantity ,double totalPrice) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    public void updateCart(User user, Book book, int quantity, double totalPrice) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
