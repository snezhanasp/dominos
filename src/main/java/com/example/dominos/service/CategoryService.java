package com.example.dominos.service;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.IngredientType;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.CategoryRepository;
import com.example.dominos.model.repositories.IngredientTypeRepository;
import com.example.dominos.model.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ItemRepository itemRepository;

    public List<CategoryWithoutItemsDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(i-> modelMapper.map(i, CategoryWithoutItemsDTO.class))
                .toList();
    }
}
