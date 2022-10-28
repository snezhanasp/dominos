package com.example.dominos.model.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class NewAddressDTO {

    private String city;
    private String street;
    private int streetNumber;
}
