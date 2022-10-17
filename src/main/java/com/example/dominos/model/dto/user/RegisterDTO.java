package com.example.dominos.model.dto.user;

import lombok.Data;

@Data
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
}
