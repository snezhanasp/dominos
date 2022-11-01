package com.example.dominos.model.dto.ingredient_types;

import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class IngredientTypeResponseDTO {
    private String type;
    private double price;
    Set<IngredientWithoutTypeDTO> ingredients;
}
