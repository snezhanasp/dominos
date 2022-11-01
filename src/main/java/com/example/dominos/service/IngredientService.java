package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient.IngredientWithoutTypeDTO;
import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.repositories.IngredientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class IngredientService extends AbstractService{

    @Autowired
    protected IngredientTypeRepository ingredientTypeRepository;
    public List<IngredientTypeResponseDTO> getAllIngredients() {
        return ingredientTypeRepository.findAll().stream()
                .map(i -> modelMapper.map(i, IngredientTypeResponseDTO.class))
                .toList();
    }

    public List<IngredientWithoutTypeDTO> getItemIngredients(long id) {
        Item item = getItemById(id);
        return ingredientRepository.findIngredientsByItems(item).stream()
                .map(i-> modelMapper.map(i, IngredientWithoutTypeDTO.class))
                .toList();
    }
}
