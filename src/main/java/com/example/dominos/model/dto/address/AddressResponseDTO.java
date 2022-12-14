package com.example.dominos.model.dto.address;

import com.example.dominos.model.dto.user.UserWithoutAddressesAndOrdersDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class AddressResponseDTO {

    private long id;
    private String city;
    private String street;
    private int streetNumber;
    private UserWithoutAddressesAndOrdersDTO user;
}
