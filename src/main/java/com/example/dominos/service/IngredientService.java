package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsDTO;
import com.example.dominos.model.entities.Ingredient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService extends AbstractService{


    public List<IngredientWithoutItemsDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(i -> modelMapper.map(i, IngredientWithoutItemsDTO.class))
                .toList();
    }
}
