package com.example.dominos.model.dto.category;

import com.example.dominos.model.dto.item.ItemWithoutCategoryDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDTO {
    private long id;
    private String name;
    private List<ItemWithoutCategoryDTO> items;
}
