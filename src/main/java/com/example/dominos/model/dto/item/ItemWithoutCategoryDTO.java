package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import lombok.Data;

import java.util.List;

@Data
public class ItemWithoutCategoryDTO {
    private long id;
    private String name;
    private double price;
    //todo image
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
