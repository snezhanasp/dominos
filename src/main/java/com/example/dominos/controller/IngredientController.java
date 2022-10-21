package com.example.dominos.controller;

import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController extends AbstractController{

    @Autowired
    IngredientService ingredientService;

    @GetMapping("/menu/items/ingredients")
    public List<IngredientTypeResponseDTO> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/menu/items/{id}/ingredients")
    public List<IngredientWithoutItemsAndTypeDTO> getItemIngredients(@PathVariable long id){
        return ingredientService.getItemIngredients(id);
    }
}
