package com.example.dominos.service;

import com.example.dominos.model.dto.address.AddressResponseDTO;
import com.example.dominos.model.dto.address.NewAddressDTO;
import com.example.dominos.model.dto.user.UserWithoutAddressesDTO;
import com.example.dominos.model.entities.Address;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractService{
    public AddressResponseDTO getById(long aid) {
        Address address = addressRepository.findById(aid).orElseThrow(() -> new NotFoundException("Address not found!"));
        AddressResponseDTO dto = modelMapper.map(address, AddressResponseDTO.class);
        dto.setUser(modelMapper.map(address.getUser(), UserWithoutAddressesDTO.class));
        return dto;
    }

    public AddressResponseDTO addNewAddress(NewAddressDTO newAddressDTO, long uid) {
        //validate data
        validateAddress(newAddressDTO);
        //check if user exists in db
        User user = getUserById(uid);
        //create new address
        Address address = modelMapper.map(newAddressDTO, Address.class);
        address.setUser(user);
        //save in db
        addressRepository.save(address);
        return modelMapper.map(address,AddressResponseDTO.class);
    }

    private void validateAddress(NewAddressDTO newAddressDTO) {
        //TODO
    }
}
