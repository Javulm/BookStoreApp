package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user where email=:email", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "select * from user where email= :email and password = :password", nativeQuery = true)
    List<User> userLogin(String email, String password);
}
