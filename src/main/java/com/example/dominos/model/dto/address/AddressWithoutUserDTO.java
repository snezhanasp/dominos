package com.example.dominos.model.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressWithoutUserDTO {

    private long id;
    private String city;
    private String street;
    private int streetNumber;
}
