package com.example.dominos.service;

import com.example.dominos.model.dto.address.AddressResponseDTO;
import com.example.dominos.model.dto.address.AddressWithoutUserDTO;
import com.example.dominos.model.dto.address.NewAddressDTO;
import com.example.dominos.model.dto.user.UserWithoutAddressesAndOrdersDTO;
import com.example.dominos.model.entities.Address;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AddressService extends AbstractService{
    public AddressResponseDTO getById(long aid, long uid) {
        //check if user owns the address
        User user = getUserById(uid);
        Address address = getAddressById(aid);
        if (user.getAddresses().stream().noneMatch(a -> a.getId() == address.getId())){
            throw new UnauthorizedException("User does not own address!");
        }
        AddressResponseDTO dto = modelMapper.map(address, AddressResponseDTO.class);
        dto.setUser(modelMapper.map(user, UserWithoutAddressesAndOrdersDTO.class));
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
        //check if user and address exist in db
        User user = getUserById(uid);
        Address address = getAddressById(dto.getId());
        //check if user owns the address
        if (user.getAddresses().stream().noneMatch(a -> a.getId() == address.getId())){
            throw new UnauthorizedException("User does not own address!");
        }
        //edit address
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
        //check if user owns the address
        if (user.getAddresses().stream().noneMatch(a -> a.getId() == aid)){
            throw new UnauthorizedException("User does not own address!");
        }
        //delete address
        addressRepository.delete(address);
        return true;
    }
}
