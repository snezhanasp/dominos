package com.example.dominos.model.dto.pizza_specification;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PizzaSpecificationDTO {
    private long id;
    private DoughDTO doughType;
    private SizeDTO size;
}
