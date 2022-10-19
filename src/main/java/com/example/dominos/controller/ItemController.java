package com.example.dominos.controller;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsDTO;
import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController extends AbstractController{

    @Autowired
    private ItemService itemService;

    @GetMapping("/menu/item/{id}")
    public ItemInfoDTO getById(@PathVariable long id){
        return itemService.getById(id);
    }

    @GetMapping("/menu/item/{id}/ingredients")
    public List<IngredientWithoutItemsDTO> getIngredientsToChooseFrom(@RequestBody ItemInfoDTO dto){
        return itemService.getAllIngredientsFor(dto);
    }


}
