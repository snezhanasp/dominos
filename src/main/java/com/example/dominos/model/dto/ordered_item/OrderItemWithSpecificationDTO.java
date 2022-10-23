package com.example.dominos.model.dto.ordered_item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemWithSpecificationDTO {
    private long itemId;
    private long sizeId;
    private long doughId;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
    private double price;
}
