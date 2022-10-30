package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.model.Book;

import java.util.List;

public interface IBookService {
    String  addBooks(BookDTO bookDTO);
    Book findBookById(int bookId);

    List<Book> findBookByName(String bookName);
    String updateBook(int bookId, BookDTO bookDTO);

    String changeBookQuantity(int bookId, int addBookQuantity, int subQuantity);

    String ChangeBookPrice(int bookId, double bookPrice);

    String deleteBook(int bookId);
}
