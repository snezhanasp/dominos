package com.example.dominos.model.dto.pizza_specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PizzaSpecificationDTO {
    private long id;
    private DoughDTO doughType;
    private SizeDTO size;
}
