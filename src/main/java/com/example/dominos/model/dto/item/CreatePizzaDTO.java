package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.pizza_specification.DoughDTO;
import com.example.dominos.model.dto.pizza_specification.SizeDTO;
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
    private DoughDTO doughType;
    private SizeDTO size;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
