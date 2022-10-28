package com.example.dominos.model.dto.ordered_item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.pizza_specification.PizzaSpecificationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {

    private long itemId;
    private PizzaSpecificationDTO pizzaSpecification;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
    private double price;
}
