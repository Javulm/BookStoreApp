package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "select * from cart where user_id= :userId", nativeQuery = true)
    List<Cart> findCartByUser(int userId);
}
