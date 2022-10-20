package com.example.dominos.service;

import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class AbstractService {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ItemRepository itemRepository;
    @Autowired
    protected IngredientTypeRepository ingredientTypeRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected IngredientRepository ingredientRepository;
    @Autowired
    protected AddressRepository addressRepository;
    @Autowired
    protected ModelMapper modelMapper;

    protected User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected Item getItemById(long id){
        return itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    protected Category getCategoryById(long id){
        return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Categoru not found"))
    }

}
