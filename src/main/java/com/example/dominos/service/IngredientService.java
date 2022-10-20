package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.entities.IngredientType;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.IngredientTypeRepository;
import com.example.dominos.model.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<IngredientTypeResponseDTO> getAllAvailableIngredientsFor(long id) {
        Optional<Item> item =  itemRepository.findById(id);
        if(!item.isPresent()){
            throw new NotFoundException("Item not found");
        }
        String category = item.get().getCategory().getName();
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
