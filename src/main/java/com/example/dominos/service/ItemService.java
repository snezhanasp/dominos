package com.example.dominos.service;

import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.dto.item.ItemWithoutCategoryDTO;
import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService extends AbstractService{

    public ItemInfoDTO getById(long id) {
        Item item = getItemById(id);
        return modelMapper.map(item,ItemInfoDTO.class);
    }

    public List<ItemWithoutCategoryDTO> getItemsForCategory(long id){
        Category category = getCategoryById(id);
        List<Item> ingredients = itemRepository.findItemsByCategory(category);
        return ingredients.stream()
                .map(i -> modelMapper.map(i, ItemWithoutCategoryDTO.class))
                .toList();
    }
}
