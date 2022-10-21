package com.example.dominos.model.dto.pizza_specification;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import lombok.Data;

import java.util.List;

@Data
public class PizzaSpecificationDTO {
    private long id;
    private DoughResponseDTO doughType;
    private SizeResponseDTO size;
}
