package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemInfoDTO {

    private long id;
    private String name;
    private double price;
    private CategoryWithoutItemsDTO category;
    private String pictureURL;
    private List<IngredientWithoutTypeDTO> ingredients;
}
