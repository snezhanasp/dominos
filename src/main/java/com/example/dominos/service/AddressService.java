package com.example.dominos.service;

import com.example.dominos.model.dto.address.AddressResponseDTO;
import com.example.dominos.model.dto.address.AddressWithoutUserDTO;
import com.example.dominos.model.dto.address.NewAddressDTO;
import com.example.dominos.model.dto.user.UserWithoutAddressesDTO;
import com.example.dominos.model.entities.Address;
import com.example.dominos.model.entities.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressService extends AbstractService{
    public AddressResponseDTO getById(long aid) {
        AddressResponseDTO dto = modelMapper.map(getAddressById(aid), AddressResponseDTO.class);
        dto.setUser(modelMapper.map(getAddressById(aid).getUser(), UserWithoutAddressesDTO.class));
        return dto;
    }

    public AddressWithoutUserDTO addNewAddress(NewAddressDTO newAddressDTO, long uid) {
        //check if user exists in db
        User user = getUserById(uid);
        //create new address
        Address address = modelMapper.map(newAddressDTO, Address.class);
        address.setUser(user);
        //save in db
        addressRepository.save(address);
        return modelMapper.map(address,AddressWithoutUserDTO.class);
    }

    public List<AddressWithoutUserDTO> getAllByUserId(long uid) {
        //check if user exists in db
        User user = getUserById(uid);
        return user.getAddresses().stream().map(a -> modelMapper.map(a, AddressWithoutUserDTO.class)).toList();
    }

    public AddressWithoutUserDTO editAddress(AddressWithoutUserDTO dto, long uid) {
        //check if user exists in db
        User user = getUserById(uid);
        //edit address
        Address address = user.getAddresses().get((int) dto.getId());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setStreetNumber(dto.getStreetNumber());
        //save in db
        addressRepository.save(address);
        return modelMapper.map(address,AddressWithoutUserDTO.class);
    }

    public boolean deleteAddress(long aid, long uid) {
        //check if user and address exist in db
        User user = getUserById(uid);
        Address address = getAddressById(aid);
        //edit address
        address.setCity("deleted at " + LocalDateTime.now());
        address.setStreet("deleted at " + LocalDateTime.now());
        user.getAddresses().remove(address);
        //save in db
        addressRepository.save(address);
        return true;
    }
}
