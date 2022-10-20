package com.example.dominos.model.dto.ingredient_types;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import lombok.Data;

import java.util.List;

@Data
public class IngredientTypeResponseDTO {
    private String type;
    private double price;
    List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
