package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.pizza_specification.DoughResponseDTO;
import com.example.dominos.model.dto.pizza_specification.SizeResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class PizzaInfoDTO {

    private long id;
    private String name;
    private double price;
    private CategoryWithoutItemsDTO category;
    private DoughResponseDTO doughType;
    private SizeResponseDTO size;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
