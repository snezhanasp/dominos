package com.example.dominos.controller;

import com.example.dominos.model.dto.category.CategoryWithoutItemsDTO;
import com.example.dominos.model.dto.ingredient_types.IngredientTypeResponseDTO;
import com.example.dominos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController extends AbstractController{

    @Autowired
    CategoryService categoryService;

    @GetMapping("/menu/categories")
    public List<CategoryWithoutItemsDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }


}
