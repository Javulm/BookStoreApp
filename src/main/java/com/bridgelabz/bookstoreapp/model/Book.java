package com.bridgelabz.bookstoreapp.model;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private String bookDescription;
    private String BookLogo;
    private double bookPrice;
    private int bookQuantity;

    public Book(BookDTO bookDTO){
        this.updateBook(bookDTO);
    }

    public void updateBook(BookDTO bookDTO){
        this.bookName = bookDTO.getBookName();
        this.bookAuthor = bookDTO.getBookAuthor();
        this.bookDescription = bookDTO.getBookDescription();
        this.BookLogo = bookDTO.getBookLogo();
        this.bookPrice = bookDTO.getBookPrice();
        this.bookQuantity = bookDTO.getBookQuantity();
    }
}
