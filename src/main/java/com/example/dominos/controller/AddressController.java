package com.example.dominos.controller;

import com.example.dominos.model.dto.address.AddressResponseDTO;
import com.example.dominos.model.dto.address.AddressWithoutUserDTO;
import com.example.dominos.model.dto.address.NewAddressDTO;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AddressController extends AbstractController{

    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses/{aid}")
    public AddressResponseDTO getById(@PathVariable long aid){
        return addressService.getById(aid);
    }

    @PostMapping("/addresses/{aid}")
    public AddressResponseDTO selectAddress(@PathVariable long aid, HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //save in session
        request.getSession().setAttribute(ADDRESS_ID,aid);
        //check if saved
        if(request.getSession().getAttribute(ADDRESS_ID) == null){
            throw new BadRequestException("Address not selected!");
        }
        return addressService.getById(aid);
    }

    @GetMapping("/addresses")
    public List<AddressWithoutUserDTO> getAllAddressesByUserId(HttpServletRequest request){
        return addressService.getAllByUserId(getLoggedUserId(request));
    }
    @PostMapping("/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressWithoutUserDTO addNewAddress(@RequestBody NewAddressDTO newAddressDTO, HttpServletRequest request){
        return addressService.addNewAddress(newAddressDTO, getLoggedUserId(request));
    }

    @PutMapping("/addresses")
    public AddressWithoutUserDTO editAddress(@RequestBody AddressWithoutUserDTO dto, HttpServletRequest request){
        return addressService.editAddress(dto, getLoggedUserId(request));
    }

    @DeleteMapping("/addresses/{aid}")
    public boolean deleteAddress(@PathVariable long aid, HttpServletRequest request){
        return addressService.deleteAddress(aid, getLoggedUserId(request));
    }

}
