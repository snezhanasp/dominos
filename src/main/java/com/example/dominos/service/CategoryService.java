package com.example.dominos.service;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends AbstractService{

    public List<CategoryWithoutItemsDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(i-> modelMapper.map(i, CategoryWithoutItemsDTO.class))
                .toList();
    }
}
