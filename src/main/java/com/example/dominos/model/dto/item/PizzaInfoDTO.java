package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import com.example.dominos.model.entities.PizzaSpecification;
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
    private PizzaSpecification.DoughType doughType;
    private PizzaSpecification.Size size;
    private List<IngredientWithoutTypeDTO> ingredients;
}
