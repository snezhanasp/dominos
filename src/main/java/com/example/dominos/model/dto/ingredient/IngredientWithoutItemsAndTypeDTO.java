package com.example.dominos.model.dto.ingredient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class IngredientWithoutItemsAndTypeDTO {
    private long id;
    private String name;
}
