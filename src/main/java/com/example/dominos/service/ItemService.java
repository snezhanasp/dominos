package com.example.dominos.service;

import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.dto.item.ItemWithoutCategoryDTO;
import com.example.dominos.model.entities.Category;
import com.example.dominos.model.entities.Item;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ItemService extends AbstractService{

    public ItemInfoDTO getById(long id) {
        Item item = getItemById(id);
        return modelMapper.map(item,ItemInfoDTO.class);
    }

    public List<ItemWithoutCategoryDTO> getItemsForCategory(long id){
        Category category = getCategoryById(id);
        return itemRepository.findItemsByCategory(category).stream()
                .map(i -> modelMapper.map(i, ItemWithoutCategoryDTO.class))
                .toList();
    }
    private Category getCategoryById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));

    }

    public String uploadItemImage(long id, MultipartFile image) {
        Item item = getItemById(id);
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String name = "uploads" + File.separator + System.nanoTime() + "." + extension;

        //copy image to file system
        File file = new File(name);
        if (!file.exists()) {
            try {
                Files.copy(image.getInputStream(),file.toPath());
            } catch (IOException e) {
                throw new BadRequestException(e.getMessage(),e);
            }
        } else {
            throw new BadRequestException("File name already exists");
        }

        //delete old image in file system if there is one
        if (item.getPictureURL() != null){
            File old = new File(item.getPictureURL());
            old.delete();
        }

        //save to db
        item.setPictureURL(name);
        itemRepository.save(item);
        return name;
    }
}
