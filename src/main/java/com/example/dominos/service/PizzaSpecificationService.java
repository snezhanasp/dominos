package com.example.dominos.service;

import com.example.dominos.model.dto.pizza_specification.DoughResponseDTO;
import com.example.dominos.model.dto.pizza_specification.SizeResponseDTO;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.repositories.DoughRepository;
import com.example.dominos.model.repositories.ItemRepository;
import com.example.dominos.model.repositories.SizeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaSpecificationService {
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    DoughRepository doughRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ModelMapper modelMapper;
    public List<SizeResponseDTO> getAllSizesForItem(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            throw new NotFoundException("Item not found");
        }
        String category = item.get().getCategory().getName();
        if (!category.equals("pizza")) { //etc
            throw new BadRequestException("No sizes available for this item");
        }
        return sizeRepository.findAll().stream()
                .map(i-> modelMapper.map(i, SizeResponseDTO.class)).toList();
    }

    public List<DoughResponseDTO> getAllDoughTypesForItem(long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (!item.isPresent()) {
            throw new NotFoundException("Item not found");
        }
        String category = item.get().getCategory().getName();
        if (!category.equals("pizza")) { //etc
            throw new BadRequestException("No dough types available for this item");
        }
        return doughRepository.findAll().stream()
                .map(i-> modelMapper.map(i, DoughResponseDTO.class)).toList();
    }
}
