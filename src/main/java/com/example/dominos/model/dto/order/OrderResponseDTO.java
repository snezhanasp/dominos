package com.example.dominos.model.dto.order;

import com.example.dominos.model.dto.PaymentMethodWithoutOrdersDTO;
import com.example.dominos.model.dto.StatusWithoutOrdersDTO;
import com.example.dominos.model.dto.address.AddressWithoutUserDTO;

import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderResponseDTO {

    private long id;
    private double price;
    private LocalDateTime orderedAt;
    private AddressWithoutUserDTO address;
    private StatusWithoutOrdersDTO status;
    private PaymentMethodWithoutOrdersDTO paymentMethod;
    private Set<CartItemWithQuantityDTO> items;
    //private Map<OrderedItemDTO, Integer> items; //TODO pizza specification?? + hashcode equals
}
