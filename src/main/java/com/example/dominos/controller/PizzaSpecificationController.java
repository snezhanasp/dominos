package com.example.dominos.controller;

import com.example.dominos.model.dto.pizza_specification.DoughResponseDTO;
import com.example.dominos.model.dto.pizza_specification.SizeResponseDTO;
import com.example.dominos.service.PizzaSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PizzaSpecificationController extends AbstractController{

    @Autowired
    PizzaSpecificationService pizzaSpecificationService;

    @GetMapping("/items/sizes")
    public List<SizeResponseDTO> getAvailableSizes(){
        return pizzaSpecificationService.getAllSizesForItem();
    }

    @GetMapping("/items/dough")
    public List<DoughResponseDTO> getAvailableDoughTypes(){
        return pizzaSpecificationService.getAllDoughTypesForItem();
    }
}
