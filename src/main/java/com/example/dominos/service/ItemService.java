package com.example.dominos.service;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsDTO;
import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private ModelMapper modelMapper;

    public ItemInfoDTO getById(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()){
            return modelMapper.map(item.get(),ItemInfoDTO.class);
        } else {
            throw new NotFoundException("Item not found");
        }
    }

    public List<IngredientWithoutItemsDTO> getAllIngredientsFor(ItemInfoDTO dto) {
        String type = dto.getCategory().getName();
        List<IngredientWithoutItemsDTO> ingredients = ingredientService.getAllIngredients();
        if(ingredients == null){
            throw new NotFoundException("No available ingredients");
        }
        return ingredients;
    }

}
