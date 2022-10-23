package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.pizza_specification.DoughResponseDTO;
import com.example.dominos.model.dto.pizza_specification.SizeResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreatePizzaDTO {

    private long itemId;
    private String name;
    private double price;
    private DoughResponseDTO doughType;
    private SizeResponseDTO size;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
