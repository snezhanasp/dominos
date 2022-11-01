package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import com.example.dominos.model.entities.PizzaSpecification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreatePizzaDTO {

    private long itemId;
    private String name;
    private double price;
    private PizzaSpecification.DoughType doughType;
    private PizzaSpecification.Size size;
    private List<IngredientWithoutTypeDTO> ingredients;
}
