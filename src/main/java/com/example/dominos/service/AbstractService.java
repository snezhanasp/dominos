package com.example.dominos.service;

import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.AddressRepository;
import com.example.dominos.model.repositories.IngredientRepository;
import com.example.dominos.model.repositories.ItemRepository;
import com.example.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ItemRepository itemRepository;
    @Autowired
    protected IngredientRepository ingredientRepository;
    @Autowired
    protected AddressRepository addressRepository;
    @Autowired
    protected ModelMapper modelMapper;

    protected User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

}
