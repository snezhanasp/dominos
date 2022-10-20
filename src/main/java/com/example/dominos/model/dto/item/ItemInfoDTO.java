package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import lombok.Data;

import java.util.List;

@Data
public class ItemInfoDTO {

    private long id;
    private String name;
    private double price;
    private CategoryWithoutItemsDTO category;
    // todo image
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
