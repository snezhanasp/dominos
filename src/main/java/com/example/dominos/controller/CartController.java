package com.example.dominos.controller;

import com.example.dominos.model.dto.cart.CartResponseDTO;
import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.item.ItemWithSpecificationAndQuantityDTO;
import com.example.dominos.model.exceptions.BadRequestException;
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
    public CartResponseDTO addItemToCart(@RequestBody ItemWithSpecificationAndQuantityDTO dto, HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //save in session
        HttpSession session = request.getSession();
        Set<CartItemWithQuantityDTO> cart;
        if(session.getAttribute(CART) == null){
            cart = new HashSet<>();
        }
        else {
            cart = (Set<CartItemWithQuantityDTO>) session.getAttribute(CART);
        }
        cart = cartService.addItemToCart(cart, dto);
        session.setAttribute(CART,cart);
        return cartService.viewCart(cart);
    }

    @GetMapping("/cart/show")
    public CartResponseDTO viewCart(HttpServletRequest request){
        getLoggedUserId(request);
        return cartService.viewCart((Set<CartItemWithQuantityDTO>) request.getSession().getAttribute(CART));
    }

    @PutMapping("/cart")
    public CartResponseDTO removeItemFromCart(@RequestBody CartItemWithQuantityDTO dto , HttpServletRequest request) {
        getLoggedUserId(request);
        HttpSession session = request.getSession();
        if (session.getAttribute(CART) == null) {
            throw new BadRequestException("Cart is empty");
        }
        Set<CartItemWithQuantityDTO> cart = (Set<CartItemWithQuantityDTO>) session.getAttribute(CART);
        return cartService.removeItemFromCart(cart, dto);
    }

    @PutMapping("/cart/empty")
    public void emptyCart(HttpServletRequest request){
        getLoggedUserId(request);
        HttpSession session = request.getSession();
        if (session.getAttribute(CART) == null) {
            throw new BadRequestException("Cart is already empty");
        }
        session.setAttribute(CART, null);
    }

}
