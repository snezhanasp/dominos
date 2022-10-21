package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class CartItemDTO {

    private long itemId;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
    private long pizzaSpecificationId;
    @EqualsAndHashCode.Exclude
    private int quantity;
}
