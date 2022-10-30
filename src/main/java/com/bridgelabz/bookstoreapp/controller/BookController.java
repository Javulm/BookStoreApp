package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.Book;
import com.bridgelabz.bookstoreapp.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private IBookService bookService;

    @PostMapping("/addbook")
    public ResponseEntity<ResponseDTO> addBook(@Valid @RequestBody BookDTO bookDTO){
        String token= bookService.addBooks(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Book details added.", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/findbookbyid")
    public ResponseEntity<ResponseDTO> findBookById(@RequestHeader int bookId){
        Book book = bookService.findBookById(bookId);
        ResponseDTO responseDTO = new ResponseDTO("Book detail updated successfully", book);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping("/findbook")
    public ResponseEntity<ResponseDTO> findBookByName(@RequestParam String bookName){
        List<Book> book = bookService.findBookByName(bookName);
        ResponseDTO responseDTO = new ResponseDTO("Books present by name "+ bookName+" are:", book);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PostMapping("/changequantity")
    public ResponseEntity<ResponseDTO> changeBookQuantity(@RequestParam int bookId,@RequestParam int addBookQuantity,@RequestParam int subBookQuantity){
        String message = bookService.changeBookQuantity(bookId, addBookQuantity,subBookQuantity);
        ResponseDTO responseDTO = new ResponseDTO("Book Quantity Updated", message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PostMapping("/changeprice")
    public ResponseEntity<ResponseDTO> changeBookPrice(@RequestParam int bookId,@RequestParam double bookPrice){
        String message = bookService.ChangeBookPrice(bookId, bookPrice);
        ResponseDTO responseDTO = new ResponseDTO("Book price Updated", message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateBook(@RequestHeader int bookId,@RequestBody BookDTO bookDTO){
        String message = bookService.updateBook(bookId, bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Book data Updated", message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteBook(@RequestHeader int bookId){
        String message = bookService.deleteBook(bookId);
        ResponseDTO responseDTO = new ResponseDTO("Book deleted successfully", message);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
