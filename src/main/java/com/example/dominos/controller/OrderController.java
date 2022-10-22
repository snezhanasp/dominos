package com.example.dominos.controller;

import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.order.CreateOrderDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class OrderController extends AbstractController{

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public OrderResponseDTO createOrder(@RequestBody CreateOrderDTO dto, HttpServletRequest request){
        long uid = getLoggedUserId(request);
        HttpSession session = request.getSession();
        if(session.getAttribute(CART) == null){
            throw new BadRequestException("Cart is empty");
        }
        Set<CartItemWithQuantityDTO> cart = (HashSet<CartItemWithQuantityDTO>) session.getAttribute(CART);
        long aid = getAddressId(session);

        //empty cart -> shouldn't be here
        session.setAttribute(CART, null);
        return orderService.createOrder(dto, cart, uid, aid);
    }

    private long getAddressId(HttpSession session){
        if(session.getAttribute(ADDRESS_ID) == null){
            throw new BadRequestException("You have to choose delivery address");
        }
        return (long) session.getAttribute(ADDRESS_ID);
    }

    @GetMapping("/orders")
    public List<OrderResponseDTO> getOrdersForUser(HttpServletRequest request){
        long uid = getLoggedUserId(request);
        return orderService.getOrdersForUser(uid);
    }


    @GetMapping("/orders/{oid}")
    public OrderResponseDTO getOrderById(@PathVariable long oid, HttpServletRequest request){
        Long uid = getLoggedUserId(request);
        return orderService.getOrderById(oid, uid);
    }
    //getById
    //getAllOrders
}
