package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.model.User;

import java.util.List;

public interface IUserService {
    User getByToken(String token);
    String userRegistration(UserDTO userDTO);
    String userLogin(String email, String password);
    String forgetPassword(String email, String password,String newPassword);
    User updateUserDataByToken(String token, UserDTO userDTO);
    String deleteUserById(int userId);
    public List<User> getAllUser();
}
