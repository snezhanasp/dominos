package com.example.dominos.model.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryWithoutItemsDTO {
    private long id;
    private String name;
    private boolean isModifiable;
}
