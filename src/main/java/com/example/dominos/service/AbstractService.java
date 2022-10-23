package com.example.dominos.service;

import com.example.dominos.model.entities.*;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
    protected OrderedItemRepository orderedItemRepository;
    @Autowired
    protected OrderRepository orderRepository;
    @Autowired
    protected StatusRepository statusRepository;
    @Autowired
    protected PaymentMethodRepository paymentMethodRepository;

    @Autowired
    protected OrderItemQuantityRepository orderItemQuantityRepository;
    @Autowired
    protected PizzaSpecificationRepository pizzaSpecificationRepository;
    @Autowired
    protected ModelMapper modelMapper;

    protected User getUserById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected Item getItemById(long id){
        return itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item not found"));
    }

    protected Address getAddressById(long id){
        return addressRepository.findById(id).orElseThrow(() -> new NotFoundException("Address not found!"));
    }

    //not here
    protected PaymentMethod getPaymentMethodById(long id){
        return paymentMethodRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment method not found!"));
    }

//    protected PizzaSpecification getPizzaSpecById(long id){
//        return pizzaSpecificationRepository.findById(id).orElseThrow(() -> new NotFoundException("Pizza specification not found!"));
//    }

    protected Ingredient getIngredientById(long id){
        return ingredientRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingredient not found"));    }

}
