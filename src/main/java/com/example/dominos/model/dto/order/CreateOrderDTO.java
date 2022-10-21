package com.example.dominos.model.dto.order;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateOrderDTO {

    private double price;
    private long paymentMethodId;

}
