package com.example.dominos.controller;

import com.example.dominos.model.dto.address.AddressResponseDTO;
import com.example.dominos.model.dto.address.NewAddressDTO;
import com.example.dominos.model.repositories.AddressRepository;
import com.example.dominos.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AddressController extends AbstractController{

    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses/{aid}")
    public AddressResponseDTO getById(@PathVariable long aid){
        return addressService.getById(aid);
    }
    @PostMapping("/addresses")
    public AddressResponseDTO addNewAddress(@RequestBody NewAddressDTO newAddressDTO, HttpServletRequest request){
        long uid = getLoggedUserId(request);
        System.out.println(uid);
        System.out.println(newAddressDTO);
        return addressService.addNewAddress(newAddressDTO, uid);
    }

    public void editAddress(){

    }

    public void deleteAddress(){

    }



}
