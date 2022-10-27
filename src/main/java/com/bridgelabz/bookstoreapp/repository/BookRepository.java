package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "select * from book where book_name like :bookName%", nativeQuery = true)
    List<Book> findBookByName(String bookName);
}
