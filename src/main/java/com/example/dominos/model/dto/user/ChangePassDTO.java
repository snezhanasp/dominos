package com.example.dominos.model.dto.user;

import lombok.Data;

@Data
public class ChangePassDTO {

    private String password;
    private String newPassword;
    private String confirmPassword;
}
