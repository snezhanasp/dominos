package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsDTO;
import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.repositories.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<IngredientWithoutItemsDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(i -> modelMapper.map(i, IngredientWithoutItemsDTO.class))
                .toList();
    }
}
