package com.example.dominos.model.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDTO {

    private double price;
    private long paymentMethodId;

}
