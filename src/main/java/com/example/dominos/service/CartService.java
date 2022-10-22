package com.example.dominos.service;

import com.example.dominos.model.dto.cart.CartResponseDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemDTO;
import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.entities.PizzaSpecification;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CartService extends AbstractService{

    public static final String PIZZA = "pizza";

    public Set<CartItemWithQuantityDTO> addItemToCart(Set<CartItemWithQuantityDTO> cart, CartItemWithQuantityDTO dto) {

        System.out.println(dto.getItem());
        Item item = getItemById(dto.getItem().getItemId());
        validateOrderItem(dto.getItem(), item);
        double price = calculatePrice(dto.getItem(), item);
        dto.getItem().setPrice(price);

        if(cart.contains(dto)){
            for(Iterator<CartItemWithQuantityDTO> it = cart.iterator(); it.hasNext();){
                CartItemWithQuantityDTO cartItemDTO = it.next();
                if(cartItemDTO.equals(dto)){
                    cartItemDTO.setQuantity(cartItemDTO.getQuantity() + dto.getQuantity());
                }
            }
            return cart;
        }
        cart.add(dto);
        return cart;
    }

    private void validateOrderItem(OrderItemDTO dto, Item item) {
        boolean hasExtraIngredients = (dto.getIngredients() != null);
        boolean hasPizzaSpecification = (dto.getPizzaSpecificationId() > 0);
        if(!item.getCategory().isModifiable() && (hasExtraIngredients || hasPizzaSpecification)){
            throw new BadRequestException("Modifications are not available for this item");
        }
        if(dto.getPizzaSpecificationId() > 0){
            if(!item.getCategory().getName().equals(PIZZA)){
                throw new BadRequestException("Dough and size modifications are not available for this item");
            }
        }
    }


    private double calculatePrice(OrderItemDTO dto, Item item) {
        double price = item.getPrice();
        if(dto.getPizzaSpecificationId() > 0) {
            price += calculatePizzaSpecificationPriceForId(dto.getPizzaSpecificationId());
        }
        if(dto.getIngredients() != null) {
            price += calculateExtraIngredientsPrice(dto, item);
        }
        return price;
    }

    private double calculatePizzaSpecificationPriceForId(long id) {
         double price = 0;
         PizzaSpecification pizzaSpecification = pizzaSpecificationRepository.findById(id)
                .orElseThrow(() ->new NotFoundException("Pizza specification not found"));
         price += pizzaSpecification.getDoughType().getPrice();
         price += pizzaSpecification.getSize().getPrice();
         return price;
    }

    private double calculateExtraIngredientsPrice(OrderItemDTO dto, Item item){
        double price = 0;

        List<IngredientWithoutItemsAndTypeDTO> ingredientsInOrder = dto.getIngredients();
        Set<Ingredient> ingredientsInItem = item.getIngredients();
        int extraIngredients = ingredientsInOrder.size() - ingredientsInItem.size();

        if(extraIngredients > 0){
            for(IngredientWithoutItemsAndTypeDTO ingredientDTO : ingredientsInOrder){
                Ingredient ingredient = getIngredientById(ingredientDTO.getId());
                if(!ingredientsInItem.contains(ingredient)){
                    price += ingredient.getIngredientType().getPrice();
                }
            }
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
