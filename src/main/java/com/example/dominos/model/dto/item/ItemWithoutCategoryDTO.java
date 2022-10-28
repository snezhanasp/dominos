package com.example.dominos.model.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemWithoutCategoryDTO {
    private long id;
    private String name;
    private double price;
    private String pictureURL;
    private List<String> ingredients;
}
