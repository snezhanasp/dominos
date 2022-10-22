package com.example.dominos.service;

import com.example.dominos.model.dto.pizza_specification.DoughResponseDTO;
import com.example.dominos.model.dto.pizza_specification.PizzaSpecificationDTO;
import com.example.dominos.model.dto.pizza_specification.SizeResponseDTO;
import com.example.dominos.model.repositories.DoughRepository;
import com.example.dominos.model.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaSpecificationService extends AbstractService{
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    DoughRepository doughRepository;

    public List<SizeResponseDTO> getAllSizesForItem() {
        return sizeRepository.findAll().stream()
                .map(i-> modelMapper.map(i, SizeResponseDTO.class)).toList();
    }

    public List<DoughResponseDTO> getAllDoughTypesForItem() {
        return doughRepository.findAll().stream()
                .map(i-> modelMapper.map(i, DoughResponseDTO.class)).toList();
    }

    public List<PizzaSpecificationDTO> getAllPizzaSpecifications() {
        return pizzaSpecificationRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PizzaSpecificationDTO.class)).toList();
    }
}
