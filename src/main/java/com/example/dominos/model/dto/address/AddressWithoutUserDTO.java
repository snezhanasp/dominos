package com.example.dominos.model.dto.address;

import lombok.Data;

@Data
public class AddressWithoutUserDTO {

    private long id;
    private String city;
    private String street;
    private int streetNumber;
}
