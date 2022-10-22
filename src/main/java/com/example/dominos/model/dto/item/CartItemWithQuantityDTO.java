package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ordered_item.OrderItemDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class CartItemWithQuantityDTO {
    private OrderItemDTO item;
    @EqualsAndHashCode.Exclude
    int quantity;
}
