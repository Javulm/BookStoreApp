package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.Util.EmailSenderService;
import com.bridgelabz.bookstoreapp.Util.TokenUtility;
import com.bridgelabz.bookstoreapp.dto.UpdateUserDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    Integer otp= null;


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
    public String resetPassword(String email, String oldPassword, String newPassword) {
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
    public String forgetPassword(String email, int Otp, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        int otp = this.otp;
        if (user.isPresent()) {
            if (Otp ==otp) {
                User user1 = user.get();
                user1.setPassword(password);
                userRepository.save(user1);
                emailSenderService.sendEmail(user.get().getEmail(),"forget password mail", "Password changed successfully");
                return "password changed";
            }else throw new BookStoreException("Please enter correct otp");
        }else throw new BookStoreException("User not found");
    }
    @Override
    public void otpGenerator(){
        Random rnd = new Random();
        this.otp = rnd.nextInt(999999);
    }
    @Override
    public String sendOTP(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            this.otpGenerator();
            int OTP = this.otp;
            emailSenderService.sendEmail(user.get().getEmail(), "OTP for password", "Please use " + OTP + " as OTP to change password");
        }else throw new BookStoreException("User not found");
        return "Otp sent to the registered mail Id";
    }



    @Override
    public User updateUserDataByToken(String token, UpdateUserDTO updateUserDTO) {
        User user = this.getByToken(token);
        user.updateUser(updateUserDTO);
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
