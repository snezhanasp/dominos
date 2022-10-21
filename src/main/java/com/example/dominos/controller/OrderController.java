package com.example.dominos.controller;

import com.example.dominos.model.dto.order.CreateOrderDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.dto.item.CartItemDTO;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RestController
public class OrderController extends AbstractController{

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public OrderResponseDTO createOrder(@RequestBody CreateOrderDTO dto, HttpServletRequest request){
        long uid = getLoggedUserId(request);
        HttpSession session = request.getSession();
        if(session.getAttribute(CART) == null){
            throw new BadRequestException("Cart is empty");
        }
        Set<CartItemDTO> cart = (HashSet<CartItemDTO>) session.getAttribute(CART);
        long aid = (long) session.getAttribute(ADDRESS_ID);
        //empty cart -> shouldn't be here
        session.setAttribute(CART, null);
        return orderService.createOrder(dto, cart, uid, aid);
    }


    //getById
    //getAllOrders
}
