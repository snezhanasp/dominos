package com.example.dominos.model.dto.user;

import lombok.Data;

@Data
public class ChangePassDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
