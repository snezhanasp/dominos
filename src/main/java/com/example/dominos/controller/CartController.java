package com.example.dominos.controller;

import com.example.dominos.model.dto.item.CartItemDTO;
import com.example.dominos.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
public class CartController extends AbstractController{

    @Autowired
    CartService cartService;

    @PostMapping("/cart") //to return smth
    public void addItemToCart(@RequestBody CartItemDTO dto, HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //save in session
        HttpSession session = request.getSession();
        Set<CartItemDTO> cart;
        if(session.getAttribute(CART) == null){
            cart = new HashSet<>();
        }
        else {
            cart = (Set<CartItemDTO>) session.getAttribute(CART);
        }
        cart = cartService.addItemToCart(cart, dto);
        session.setAttribute(CART,cart);
    }

//    @PutMapping("/cart")
//    public void changeQuantity(@RequestBody CartItemDTO dto, HttpServletRequest request){
//        //check if logged
//        getLoggedUserId(request);
//        //get cart from session
//        HttpSession session = request.getSession();
//        Map<CartItemDTO, Integer> cart = (HashMap<CartItemDTO, Integer>) session.getAttribute(CART);
//        //check if cart contains item
//        if (cart.containsKey(dto)){
//            cart.put(dto, dto.getQuantity());
//        } else {
//            throw new BadRequestException("No such item in cart!");
//        }
//        //save in session
//        session.setAttribute(CART,cart);
//    }

//    @DeleteMapping("/cart")
//    public void deleteItem(@RequestBody CartItemDTO dto, HttpServletRequest request){
//        //check if logged
//        getLoggedUserId(request);
//        //get cart from session
//        HttpSession session = request.getSession();
//        Map<CartItemDTO, Integer> cart = (HashMap<CartItemDTO, Integer>) session.getAttribute(CART);
//        //check if cart contains item
//        if (cart.containsKey(dto)){
//            cart.remove(dto);
//        } else {
//            throw new BadRequestException("No such item in cart!");
//        }
//        //save in session
//        session.setAttribute(CART,cart);
//    }

}
