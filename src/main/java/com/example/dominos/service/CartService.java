package com.example.dominos.service;

import com.example.dominos.model.dto.cart.CartResponseDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.item.ItemWithSpecificationAndQuantityDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemWithSpecificationDTO;
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
import java.util.Optional;
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

        long specificationId = getPizzaSpecificationId(dto.getItem().getSizeId(), dto.getItem().getDoughId());
        cartItem.getItem().setPizzaSpecificationId(specificationId);

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

    private long getPizzaSpecificationId(long sizeId, long doughId){
        Optional<PizzaSpecification> specification = pizzaSpecificationRepository.findBySize_IdAndDoughType_Id(sizeId, doughId);
        if(specification.isPresent()){
            return specification.get().getId();
        }
        PizzaSpecification newSpecification = new PizzaSpecification();
        newSpecification.setSize(getSizeById(sizeId));
        newSpecification.setDoughType(getDoughById(doughId));
        pizzaSpecificationRepository.save(newSpecification);
        return newSpecification.getId();
    }

    private PizzaSpecification.Size getSizeById(long id){
        return sizeRepository.findById(id).orElseThrow(()-> new NotFoundException("Size not found"));
    }

    private PizzaSpecification.DoughType getDoughById(long id){
        return doughRepository.findById(id).orElseThrow(()->new NotFoundException("Dough not found"));
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
