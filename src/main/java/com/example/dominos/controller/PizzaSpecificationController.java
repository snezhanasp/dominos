package com.example.dominos.controller;

import com.example.dominos.model.dto.pizza_specification.DoughDTO;
import com.example.dominos.model.dto.pizza_specification.SizeDTO;
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
    public List<SizeDTO> getAvailableSizes(){
        return pizzaSpecificationService.getAllSizes();
    }

    @GetMapping("/items/dough")
    public List<DoughDTO> getAvailableDoughTypes(){
        return pizzaSpecificationService.getAllDoughTypes();
    }
}
