package com.example.dominos.service;

import com.example.dominos.model.dto.cart.CartResponseDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.item.ItemWithSpecificationAndQuantityDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemWithSpecificationDTO;
import com.example.dominos.model.dto.pizza_specification.PizzaSpecificationDTO;
import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.entities.PizzaSpecification;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.DoughRepository;
import com.example.dominos.model.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CartService extends AbstractService{

    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    DoughRepository doughRepository;

    public static final String PIZZA = "pizza";

    public Set<CartItemWithQuantityDTO> addItemToCart(Set<CartItemWithQuantityDTO> cart, ItemWithSpecificationAndQuantityDTO dto) {

        Item item = getItemById(dto.getItem().getItemId());
        validateOrderItem(dto.getItem(), item);
        CartItemWithQuantityDTO cartItem = modelMapper.map(dto, CartItemWithQuantityDTO.class);

        if(item.getCategory().equals(PIZZA)) {
            PizzaSpecification specification = getPizzaSpecificationBySizeIdAndDoughId(dto.getItem().getSizeId(), dto.getItem().getDoughId());
            cartItem.getItem().setPizzaSpecification(modelMapper.map(specification, PizzaSpecificationDTO.class));
        }

        double price = calculatePrice(cartItem.getItem(), item);
        cartItem.getItem().setPrice(price);

        if(cart.contains(cartItem)){
            for(Iterator<CartItemWithQuantityDTO> it = cart.iterator(); it.hasNext();){
                CartItemWithQuantityDTO cartItemDTO = it.next();
                if(cartItemDTO.equals(cartItem)){
                    cartItemDTO.setQuantity(cartItemDTO.getQuantity() + cartItem.getQuantity());
                }
            }
            return cart;
        }
        cart.add(cartItem);
        return cart;
    }

    private void validateOrderItem(OrderItemWithSpecificationDTO dto, Item item) {
        boolean hasExtraIngredients = (dto.getIngredients() != null);
        boolean hasSize = (dto.getSizeId() > 0);
        boolean hasDoughType = (dto.getDoughId() > 0);
        if(!item.getCategory().isModifiable() && (hasExtraIngredients || hasSize || hasDoughType)){
            throw new BadRequestException("Modifications are not available for this item");
        }
        if(!item.getCategory().getName().equals(PIZZA)){
            if(hasSize || hasDoughType) {
                throw new BadRequestException("Dough and size modifications are not available for this item");
            }
        }
        else{
            if(!hasSize){
                throw new BadRequestException("You have to choose size");
            }
            if(!hasDoughType){
                throw new BadRequestException("You have to choose dough");
            }
        }
    }


    private double calculatePrice(OrderItemDTO dto, Item item) {
        double price = item.getPrice();
        if(dto.getPizzaSpecification() != null) {
            price += calculatePizzaSpecificationPrice(dto.getPizzaSpecification());
        }
        if(dto.getIngredients() != null) {
            price += calculateExtraIngredientsPrice(dto, item);
        }
        return price;
    }

    protected double calculatePizzaSpecificationPrice(PizzaSpecificationDTO specification) {
         double price = 0;
         price += specification.getDoughType().getPrice();
         price += specification.getSize().getPrice();
         return price;
    }

    private double calculateExtraIngredientsPrice(OrderItemDTO dto, Item item){
        double price = 0;
        int extraIngredients = dto.getIngredients().size() - item.getIngredients().size();

        if(extraIngredients > 0){
            price += extraIngredients * 2; //each addition is 2lv
        }
        return price;
    }

    public CartResponseDTO viewCart(Set<CartItemWithQuantityDTO> cart) {
        return new CartResponseDTO(cart);
    }

    public CartResponseDTO removeItemFromCart(Set<CartItemWithQuantityDTO> cart, CartItemWithQuantityDTO dto) {
        if(!cart.contains(dto)) {
            throw new BadRequestException("Item not found");
        }

        for(Iterator<CartItemWithQuantityDTO> it = cart.iterator(); it.hasNext();){
            CartItemWithQuantityDTO cartItemDTO = it.next();
            if(cartItemDTO.equals(dto)){
                cartItemDTO.setQuantity(cartItemDTO.getQuantity() - dto.getQuantity());
            }
            if(cartItemDTO.getQuantity() <= 0){
                cart.remove(dto);
            }
        }
        return new CartResponseDTO(cart);
    }
}
