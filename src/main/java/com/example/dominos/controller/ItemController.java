package com.example.dominos.controller;

import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.dto.item.ItemWithoutCategoryDTO;
import com.example.dominos.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ItemController extends AbstractController{

    @Autowired
    private ItemService itemService;

    @GetMapping("/menu/items/{id}")
    public ItemInfoDTO getById(@PathVariable long id){
        return itemService.getById(id);
    }

    @GetMapping("/menu/categories/{id}/items")
    public List<ItemWithoutCategoryDTO> getItemsForCategory(@PathVariable long id){
        return  itemService.getItemsForCategory(id);
    }

    @PostMapping("/menu/items/{fid}/image")
    public String uploadItemImage(@PathVariable long fid, @RequestParam(value = "image") MultipartFile image, HttpServletRequest request){
        return itemService.uploadItemImage(fid,image, getLoggedUserId(request));
    }

}
