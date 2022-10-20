package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.model.entities.IngredientType;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class IngredientService extends AbstractService{

    public List<IngredientTypeResponseDTO> getAllAvailableIngredientsFor(long id) {
        Item item = getItemById(id);
        String category = item.getCategory().getName();
        if(!category.equals("pizza") && !category.equals("salad")) {  //etc
            throw new BadRequestException("Items from this category cannot be modified");
            // todo return null?
        }
        List<IngredientType> ingredients = ingredientTypeRepository.findAll();
        return ingredients.stream()
                .map(i -> modelMapper.map(i, IngredientTypeResponseDTO.class))
                .toList();
    }
}
