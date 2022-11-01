package com.example.dominos.model.dto.ordered_item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import com.example.dominos.model.entities.PizzaSpecification;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemDTO {

    private long itemId;
    private PizzaSpecification pizzaSpecification;
    private List<IngredientWithoutTypeDTO> ingredients;
    private double price;
}
