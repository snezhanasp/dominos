package com.example.dominos.model.dto.ordered_item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemWithSpecificationDTO {

    private long itemId;
    private long sizeId;
    private long doughId;
    private List<IngredientWithoutTypeDTO> ingredients;
    private double price;
}
