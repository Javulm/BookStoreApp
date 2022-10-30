package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.Util.EmailSenderService;
import com.bridgelabz.bookstoreapp.Util.TokenUtility;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private EmailSenderService emailSenderService;


    @Override
    public User getByToken(String token) {
        int id = tokenUtility.decodeJWT(token);
        return userRepository.findById(id).orElseThrow(() -> new BookStoreException("User data not found"));
    }

    @Override
    public String userRegistration(UserDTO userDTO) {
        Optional<User> isEmail = userRepository.findByEmail(userDTO.getEmail());
        if (!isEmail.isPresent()) {
            User user = modelMapper.map(userDTO, User.class);
            userRepository.save(user);
            String token = tokenUtility.createToken(user.getUserId());
            emailSenderService.sendEmail(user.getEmail(), "Registration mail", "Hey " + user.getFirstName() + ", Thank you for registering with us");
            return token;
        } else throw new BookStoreException("User with emailId " + userDTO.getEmail() + " is present");
    }

    @Override
    public String userLogin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String pwd = user.get().getPassword();
            if (pwd.equals(password)) {
                return "User Login successfull";
            } else {
                return "wrong password";
            }
        }
        return "User not found";
    }

    @Override
    public String forgetPassword(String email, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String password = user.get().getPassword();
            if (password.equals(oldPassword)) {
                User user1 = user.get();
                user1.setPassword(newPassword);
                userRepository.save(user1);
                emailSenderService.sendEmail(user1.getEmail(), "Password Reset mail", "User password reset successfully");
                return "Password updated successfully";
            }else throw new BookStoreException("Please enter correct password");
        }
        return "User not found";
    }

    @Override
    public User updateUserDataByToken(String token, UserDTO userDTO) {
        User user = this.getByToken(token);
        user.updateUser(userDTO);
        emailSenderService.sendEmail(user.getEmail(), "Update mail", "User data updated successfully");
        return userRepository.save(user);
    }

    @Override
    public String deleteUserById(int userId) {
        userRepository.findById(userId);
        userRepository.deleteById(userId);
        return "User data deleted";
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
}
