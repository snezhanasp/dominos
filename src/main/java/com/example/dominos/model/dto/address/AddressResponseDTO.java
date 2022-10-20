package com.example.dominos.model.dto.address;

import com.example.dominos.model.dto.user.UserWithoutAddressesDTO;
import lombok.Data;

@Data
public class AddressResponseDTO {

    private long id;
    private String city;
    private String street;
    private int streetNumber;
    private UserWithoutAddressesDTO user;
}
