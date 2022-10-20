package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsDTO;
import lombok.Data;

import java.util.List;

@Data
public class ItemInfoDTO {

    private String name;
    private CategoryWithoutItemsDTO category;
    private double price;

    //todo image
    private List<IngredientWithoutItemsDTO> ingredients;
}
