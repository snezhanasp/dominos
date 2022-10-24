package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.model.entities.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class IngredientService extends AbstractService{

    public List<IngredientTypeResponseDTO> getAllIngredients() {
        return ingredientTypeRepository.findAll().stream()
                .map(i -> modelMapper.map(i, IngredientTypeResponseDTO.class))
                .toList();
    }

    public List<IngredientWithoutItemsAndTypeDTO> getItemIngredients(long id) {
        Item item = getItemById(id);
        return ingredientRepository.findIngredientsByItems(item).stream()
                .map(i-> modelMapper.map(i, IngredientWithoutItemsAndTypeDTO.class))
                .toList();
    }
}
