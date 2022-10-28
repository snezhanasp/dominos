package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
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
public class PizzaInfoDTO {

    private long id;
    private String name;
    private double price;
    private CategoryWithoutItemsDTO category;
    private DoughDTO doughType;
    private SizeDTO size;
    private List<IngredientWithoutItemsAndTypeDTO> ingredients;
}
