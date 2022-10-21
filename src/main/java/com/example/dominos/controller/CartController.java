package com.example.dominos.controller;

import com.example.dominos.model.dto.item.CartItemDTO;
import com.example.dominos.model.exceptions.BadRequestException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CartController extends AbstractController{

    @PostMapping("/cart")
    public void addItemToCart(@RequestBody CartItemDTO dto, HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //save in session
        HttpSession session = request.getSession();
        Map<CartItemDTO, Integer> cart = (HashMap<CartItemDTO, Integer>) session.getAttribute(CART);
        cart.put(dto, dto.getQuantity());
        session.setAttribute(CART,cart);
    }

    @PutMapping("/cart")
    public void changeQuantity(@RequestBody CartItemDTO dto, HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //get cart from session
        HttpSession session = request.getSession();
        Map<CartItemDTO, Integer> cart = (HashMap<CartItemDTO, Integer>) session.getAttribute(CART);
        //check if cart contains item
        if (cart.containsKey(dto)){
            cart.put(dto, dto.getQuantity());
        } else {
            throw new BadRequestException("No such item in cart!");
        }
        //save in session
        session.setAttribute(CART,cart);
    }

    @DeleteMapping("/cart")
    public void deleteItem(@RequestBody CartItemDTO dto, HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //get cart from session
        HttpSession session = request.getSession();
        Map<CartItemDTO, Integer> cart = (HashMap<CartItemDTO, Integer>) session.getAttribute(CART);
        //check if cart contains item
        if (cart.containsKey(dto)){
            cart.remove(dto);
        } else {
            throw new BadRequestException("No such item in cart!");
        }
        //save in session
        session.setAttribute(CART,cart);
    }

}
