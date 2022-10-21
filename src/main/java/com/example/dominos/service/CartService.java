package com.example.dominos.service;

import com.example.dominos.model.dto.item.CartItemDTO;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
public class CartService extends AbstractService{

    public Set<CartItemDTO> addItemToCart(Set<CartItemDTO> cart,CartItemDTO dto) {
        getItemById(dto.getItemId());
        // but we don't actually need the item
        // don't know if its good or bad because following the SOLID principles
        //maybe??
        //if(itemRepository.existsById(dto.getItemId()));

        if(cart.contains(dto)){
            //the only way to find the element position is to iterate
            //which is O(N) but if it's a list is the same thing, to find if the list
            //contains the dto, it may be map but the dto should not have quantity
            for(Iterator<CartItemDTO> it = cart.iterator(); it.hasNext();){
                CartItemDTO cartItemDTO = it.next();
                if(cartItemDTO.equals(dto)){
                    cartItemDTO.setQuantity(cartItemDTO.getQuantity() + dto.getQuantity());
                }
            }
            return cart;
        }
        cart.add(dto);
        return cart;
    }
}
