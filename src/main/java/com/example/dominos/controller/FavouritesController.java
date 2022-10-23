package com.example.dominos.controller;

import com.example.dominos.model.dto.item.CreatePizzaDTO;
import com.example.dominos.model.dto.item.PizzaInfoDTO;
import com.example.dominos.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class FavouritesController extends AbstractController{

    @Autowired
    private FavouritesService favouritesService;

    @PostMapping("/favourites")
    public PizzaInfoDTO addPizzaToFavourites(@RequestBody CreatePizzaDTO dto, HttpServletRequest request){
        long uid = getLoggedUserId(request);
        return favouritesService.addNewPizza(dto, uid);
    }

    @DeleteMapping("/favourites/{pid}")
    public boolean removePizzaFromFavourites(@PathVariable long pid, HttpServletRequest request){
        long uid = getLoggedUserId(request);
        return favouritesService.removePizzaFromFavourites(pid, uid);
    }

    @GetMapping("/favourites")
    public List<PizzaInfoDTO> getFavouritesByUser(HttpServletRequest request){
        long uid = getLoggedUserId(request);
        return favouritesService.getAllFavourites(uid);
    }
}
