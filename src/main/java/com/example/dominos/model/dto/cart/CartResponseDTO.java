package com.example.dominos.model.dto.cart;

import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class CartResponseDTO {
    Set<CartItemWithQuantityDTO> items;

    public CartResponseDTO(Set<CartItemWithQuantityDTO> items) {
        this.items = items;
    }
}
