package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.Util.TokenUtility;
import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.Book;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public String addBooks(BookDTO bookDTO) {
        Book book = new Book(bookDTO);
        bookRepository.save(book);
        return "Book added successfully";
    }

    @Override
    public Book findBookById(int bookId) {
        return bookRepository.findById(bookId).orElseThrow(()-> new BookStoreException("Book not found"));
    }

    @Override
    public List<Book> findBookByName(String bookName) {
        List<Book> book = bookRepository.findBookByName(bookName);
        if (!book.isEmpty()) {
            return book;
        } else throw new BookStoreException("Book with name " + bookName + " is not available.");
    }

    @Override
    public String updateBook(int bookId, BookDTO bookDTO) {
        Book book= this.findBookById(bookId);
        book.updateBook(bookDTO);
        bookRepository.save(book);
        return "Book details updated successfully";
    }


    @Override
    public String changeBookQuantity(int bookId, int addBookQuantity,int subQuantity ) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            if(book.get().getBookQuantity() > 0){
                book.get().setBookQuantity(book.get().getBookQuantity()-subQuantity);
            }else throw new BookStoreException("Book quantity cannot be zero");
            book.get().setBookQuantity(book.get().getBookQuantity()+addBookQuantity);
            bookRepository.save(book.get());
            return "Book quantity has been changed";
        } else throw new BookStoreException("Book record does not exists");
    }

    @Override
    public String ChangeBookPrice(int bookId, double bookPrice) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            book.get().setBookPrice(bookPrice);
            bookRepository.save(book.get());
            return "Book price has been changed";
        } else throw new BookStoreException("Book record does not exists");
    }

    @Override
    public String deleteBook(int bookId) {
        Book book = this.findBookById(bookId);
            bookRepository.delete(book);
        return "Book deleted successfully";
    }
}
