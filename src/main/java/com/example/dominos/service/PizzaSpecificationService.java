package com.example.dominos.service;

import com.example.dominos.model.dto.pizza_specification.DoughDTO;
import com.example.dominos.model.dto.pizza_specification.SizeDTO;
import com.example.dominos.model.repositories.DoughRepository;
import com.example.dominos.model.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaSpecificationService extends AbstractService{
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    DoughRepository doughRepository;

    public List<SizeDTO> getAllSizes() {
        return sizeRepository.findAll().stream()
                .map(i-> modelMapper.map(i, SizeDTO.class)).toList();
    }

    public List<DoughDTO> getAllDoughTypes() {
        return doughRepository.findAll().stream()
                .map(i-> modelMapper.map(i, DoughDTO.class)).toList();
    }


}
