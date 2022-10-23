package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemWithSpecificationDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class ItemWithSpecificationAndQuantityDTO {

    private OrderItemWithSpecificationDTO item;
    private int quantity;
}
