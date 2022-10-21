package com.example.dominos.service;

import com.example.dominos.model.compositeKeys.OrderOrderedItemKey;
import com.example.dominos.model.dto.order.CreateOrderDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.dto.item.CartItemDTO;
import com.example.dominos.model.dto.ordered_item.OrderedItemDTO;
import com.example.dominos.model.entities.*;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService extends AbstractService{

    public static final int ACCEPTED = 1;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO dto, Set<CartItemDTO> cart, long uid, long aid) {

        //create order and save in order repo
        if(cart.isEmpty()){
            throw new BadRequestException("Cart is empty!");
        }
        // todo
        double totalPrice = calculatePrice(cart);
        if(totalPrice < 10){
            throw new BadRequestException("Order has to be at least 10lv.");
        }

        Order order = saveOrder(dto, uid, aid);

        Set<CartItemDTO> items = cart;
        for (CartItemDTO cartItemDTO: cart) {
            OrderedItem orderedItem = saveItem(cartItemDTO);
            int quantity = cartItemDTO.getQuantity();
            saveOrderOrderedItemRelation(order, orderedItem, quantity);
        }

        OrderResponseDTO orderResponseDTO = modelMapper.map(order,OrderResponseDTO.class);
        return orderResponseDTO;
    }

    private double calculatePrice(Set<CartItemDTO> cart) {
        double price = 0;
        for(CartItemDTO dto : cart){
            price += getItemById(dto.getItemId()).getPrice();
        }
        return price;
    }

    private void saveOrderOrderedItemRelation(Order order, OrderedItem orderedItem, int quantity) {
        OrderOrderedItemKey key = new OrderOrderedItemKey(order.getId(), orderedItem.getId());
        OrderItemQuantity orderItemQuantity = new OrderItemQuantity();
        orderItemQuantity.setId(key);
        orderItemQuantity.setOrder(order);
        orderItemQuantity.setOrderedItem(orderedItem);
        orderItemQuantity.setQuantity(quantity);
    }

    private Status getStatusById(long id){
        return statusRepository.findById(id).orElseThrow(() -> new NotFoundException("Order status not found!"));
    }

    private OrderedItem saveItem(CartItemDTO dto){
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setItem(getItemById(dto.getItemId()));
        // todo
        orderedItem.setPizzaSpecification(getPizzaSpecification(dto.getPizzaSpecificationId()));
        orderedItem.setIngredients(extractIngredients(dto));
        orderedItemRepository.save(orderedItem);
        return orderedItem;
    }

    private Order saveOrder(CreateOrderDTO dto, long uid, long aid){
        Order order = new Order();
        order.setPrice(dto.getPrice());
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(getStatusById(ACCEPTED));
        order.setPayment(getPaymentMethodById(dto.getPaymentMethodId()));
        order.setUser(getUserById(uid));
        order.setAddress(getAddressById(aid));
        orderRepository.save(order);
        return order;
    }

    private List<Ingredient> extractIngredients(CartItemDTO dto){
        return dto.getIngredients().stream()
                .map(i -> getIngredientById(i.getId()))
                .toList();
    }
    private PizzaSpecification getPizzaSpecification(long id){
        return pizzaSpecificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pizza specification not found"));
    }

}
