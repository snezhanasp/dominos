package com.example.dominos.model.dto.item;

import com.example.dominos.model.dto.ordered_item.OrderItemWithSpecificationDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemWithSpecificationAndQuantityDTO {

    private OrderItemWithSpecificationDTO item;
    private int quantity;
}
