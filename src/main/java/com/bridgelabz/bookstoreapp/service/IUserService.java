package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.UpdateUserDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.model.User;

import java.util.List;

public interface IUserService {
    User getByToken(String token);
    String userRegistration(UserDTO userDTO);
    String userLogin(String email, String password);
    String resetPassword(String email, String password,String newPassword);
    String forgetPassword(String email, int OTP, String Password);
    String sendOTP(String email);
    public void otpGenerator();
    User updateUserDataByToken(String token, UpdateUserDTO updateUserDTO);
    String deleteUserById(int userId);
    List<User> getAllUser();
}
