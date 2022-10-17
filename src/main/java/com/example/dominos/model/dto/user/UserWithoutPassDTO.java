package com.example.dominos.model.dto.user;

import lombok.Data;

@Data
public class UserWithoutPassDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

}
