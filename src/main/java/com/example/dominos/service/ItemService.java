package com.example.dominos.service;

import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.dto.item.ItemWithoutCategoryDTO;
import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends AbstractService{

    public ItemInfoDTO getById(long id) {
        Item item = getItemById(id);
        return modelMapper.map(item,ItemInfoDTO.class);
    }

    public List<ItemWithoutCategoryDTO> getItemsForCategory(long id){
        Category category = getCategoryById(id);
        return itemRepository.findItemsByCategory(category).stream()
                .map(i -> modelMapper.map(i, ItemWithoutCategoryDTO.class))
                .toList();
    }
    private Category getCategoryById(long id){
        return categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found"));
    }
}
