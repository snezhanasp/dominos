package com.example.dominos.model.dto.pizza_specification;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DoughDTO {
    private long id;
    private String name;
    private double price;
}
