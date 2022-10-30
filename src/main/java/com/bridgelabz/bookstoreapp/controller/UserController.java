package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import com.bridgelabz.bookstoreapp.model.User;
import com.bridgelabz.bookstoreapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseDTO> userRegistration(@Valid @RequestBody UserDTO userDTO) {
        String token = userService.userRegistration(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User registration successful.", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestHeader String email, @RequestHeader String password) {
        String token = userService.userLogin(email, password);
        ResponseDTO responseDTO = new ResponseDTO("Login page.", token);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getuserbytoken")
    public ResponseEntity<ResponseDTO> getAllUserById(@RequestHeader String token) {
        User user = userService.getByToken(token);
        ResponseDTO responseDTO = new ResponseDTO("User data retrieved successfully", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUserById(@PathVariable String token, @Valid @RequestBody UserDTO userDTO) {
        User updateUser = userService.updateUserDataByToken(token, userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User data updated successfully", updateUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestHeader String email, @RequestHeader String password, @RequestHeader String newPassword) {
        String response = userService.forgetPassword(email, password, newPassword);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserById(@RequestHeader int userId) {
        String response = userService.deleteUserById(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllUser() {
        List<User> user = userService.getAllUser();
        ResponseDTO responseDTO = new ResponseDTO("User data", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
