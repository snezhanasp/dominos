package com.example.dominos.model.dto.user;

import com.example.dominos.model.dto.address.AddressWithoutUserDTO;
import com.example.dominos.model.entities.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserWithoutPassDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<AddressWithoutUserDTO> addresses;
    private List<Order> orders;

}
