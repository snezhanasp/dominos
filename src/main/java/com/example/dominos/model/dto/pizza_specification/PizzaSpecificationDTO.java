package com.example.dominos.model.dto.pizza_specification;

import lombok.Data;

@Data
public class PizzaSpecificationDTO {
    private long id;
    private DoughDTO doughType;
    private SizeDTO size;
}
