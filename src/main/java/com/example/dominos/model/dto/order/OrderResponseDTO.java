package com.example.dominos.model.dto.order;

import com.example.dominos.model.dto.PaymentMethodWithoutOrdersDTO;
import com.example.dominos.model.dto.StatusWithoutOrdersDTO;
import com.example.dominos.model.dto.address.AddressWithoutUserDTO;

import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {

    private long id;
    private double price;
    private LocalDateTime orderedAt;
    private AddressWithoutUserDTO address;
    private StatusWithoutOrdersDTO status;
    private PaymentMethodWithoutOrdersDTO paymentMethod;
    private Set<CartItemWithQuantityDTO> items;
}
