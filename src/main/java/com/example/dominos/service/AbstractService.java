package com.example.dominos.service;

import com.example.dominos.model.entities.*;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {

    public static final int ACCEPTED = 1;
    public static final int READY_FOR_DELIVERY = 2;
    public static final int CANCELED = 4;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ItemRepository itemRepository;
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
    protected PizzaSpecificationRepository pizzaSpecificationRepository;
    @Autowired
    protected FavouriteItemRepository favouriteItemRepository;
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

    protected PizzaSpecification getPizzaSpecificationBySizeIdAndDoughId(long sizeId, long doughId){
        return pizzaSpecificationRepository.findBySize_IdAndDoughType_Id(sizeId, doughId)
                .orElseThrow(()->new NotFoundException("Pizza specification with this size and dough not found!"));
    }
    protected Status getStatusById(long id){
        return statusRepository.findById(id).orElseThrow(() -> new NotFoundException("Status not found!"));
    }

    protected Order getOrderById(long id){
        return orderRepository.findById(id).orElseThrow(()-> new NotFoundException("Order not found"));
    }
}
