package com.example.dominos.service;

import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.dto.item.ItemWithoutCategoryDTO;
import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.CategoryRepository;
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
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ItemInfoDTO getById(long id) {
        Item item = findValidItem(id);
        return modelMapper.map(item,ItemInfoDTO.class);
    }

    public List<ItemWithoutCategoryDTO> getItemsForCategory(long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()){
            throw new NotFoundException("Category is missing");
        }
        List<Item> ingredients = itemRepository.findItemsByCategory(category.get());
        return ingredients.stream()
                .map(i -> modelMapper.map(i, ItemWithoutCategoryDTO.class))
                .toList();
    }

    private Item findValidItem(long id){
        Optional<Item> item = itemRepository.findById(id);
        if(!item.isPresent()) {
            throw new NotFoundException("Item not found");
        }
        return item.get();
    }

}
