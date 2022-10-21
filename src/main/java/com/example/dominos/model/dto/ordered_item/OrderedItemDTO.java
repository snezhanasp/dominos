package com.example.dominos.model.dto.ordered_item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.entities.PizzaSpecification;
import lombok.Data;

import java.util.List;

@Data
public class OrderedItemDTO {

    private long id;
    private String name;
    private PizzaSpecification pizzaSpecification;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
