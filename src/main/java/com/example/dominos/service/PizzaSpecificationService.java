package com.example.dominos.service;

import com.example.dominos.model.entities.PizzaSpecification;
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

    public List<PizzaSpecification.Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    public List<PizzaSpecification.DoughType> getAllDoughTypes() {
        return doughRepository.findAll();
    }


}
