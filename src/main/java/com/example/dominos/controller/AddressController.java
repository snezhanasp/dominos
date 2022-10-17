package com.example.dominos.controller;

import com.example.dominos.model.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController extends AbstractController{

    @Autowired
    private AddressRepository addressRepository;

}
