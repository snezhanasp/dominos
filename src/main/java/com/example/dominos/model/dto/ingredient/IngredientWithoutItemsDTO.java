package com.example.dominos.model.dto.ingredient;

import com.example.dominos.model.dto.type.TypeResponseDTO;
import lombok.Data;

@Data
public class IngredientWithoutItemsDTO {
    private long id;
    private String name;
    private TypeResponseDTO type;
    private double price;
}
