package com.bridgelabz.bookstoreapp.model;

import com.bridgelabz.bookstoreapp.dto.UpdateUserDTO;
import com.bridgelabz.bookstoreapp.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
    private String firstName;
    private String lastName;
    private String Email;
    private String number;
    private String password;

    public void updateUser(UpdateUserDTO updateUserDTO) {
        this.firstName = updateUserDTO.getFirstName();
        this.lastName = updateUserDTO.getLastName();
        this.number = updateUserDTO.getNumber();
    }
}
