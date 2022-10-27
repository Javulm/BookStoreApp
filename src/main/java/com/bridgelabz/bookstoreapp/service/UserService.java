package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.Util.EmailSenderService;
import com.bridgelabz.bookstoreapp.Util.TokenUtility;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.exception.UserException;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenUtility tokenUtility;
    @Autowired
    private EmailSenderService emailSenderService;


    @Override
    public User getByToken(String token) {
        int id = tokenUtility.decodeJWT(token);
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User data not found"));
        return user;
    }

    @Override
    public String userRegistration(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
        String token = tokenUtility.createToken(user.getUserId());
        emailSenderService.sendEmail(user.getEmail(), "Registration mail", "Hey " + user.getFirstName() + " Thank you for registering with us");
        return token;
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
    public String forgetPassword(String email, String password, String newPassword) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            String oldPassword = user.get().getPassword();
            if (oldPassword.equals(password)){
                User user1 = user.get();
                user1.setPassword(newPassword);
                userRepository.save(user1);
                emailSenderService.sendEmail(user1.getEmail(),"Password Reset mail","User password reset successfully");
                return "Password updated successfully";
            }
        }
        return "User not found";
    }

    @Override
    public User updateUserDataByToken(String token, UserDTO userDTO){
        User user = this.getByToken(token);
        user.updateUser(userDTO);
        emailSenderService.sendEmail(user.getEmail(),"Update mail","User data updated successfully");
        return userRepository.save(user);
    }
}
