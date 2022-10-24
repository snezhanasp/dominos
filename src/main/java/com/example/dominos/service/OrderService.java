package com.example.dominos.service;

import com.example.dominos.model.compositeKeys.OrderOrderedItemKey;
import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.order.CreateOrderDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemDTO;
import com.example.dominos.model.entities.*;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService extends AbstractService{

    public static final int ACCEPTED = 1;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO dto, Set<CartItemWithQuantityDTO> cart, long uid, long aid) {

        //create order and save in order repo
        if(cart.isEmpty()){
            throw new BadRequestException("Cart is empty!");
        }
        double totalPrice = calculatePrice(cart);
        if(totalPrice < 10){
            throw new BadRequestException("Order has to be at least 10lv.");
        }

        Order order = saveOrder(dto, uid, aid);

        Set<CartItemWithQuantityDTO> items = cart;
        for (CartItemWithQuantityDTO cartItemDTO: cart) {
            OrderedItem orderedItem = saveItem(cartItemDTO.getItem());
            int quantity = cartItemDTO.getQuantity();
            saveOrderOrderedItemRelation(order, orderedItem, quantity);
        }

        OrderResponseDTO orderResponseDTO = modelMapper.map(order,OrderResponseDTO.class);
        orderResponseDTO.setItems(items);
        return orderResponseDTO;
    }

    private double calculatePrice(Set<CartItemWithQuantityDTO> cart) {
        double price = 0;
        for(CartItemWithQuantityDTO dto : cart){
            price += (dto.getItem().getPrice()* dto.getQuantity());
        }
        return price;
    }

    private void saveOrderOrderedItemRelation(Order order, OrderedItem orderedItem, int quantity) {
        OrderOrderedItemKey key = new OrderOrderedItemKey();

        key.setOrderId(order.getId());
        key.setOrderedItemId(orderedItem.getId());

        OrderItemQuantity orderItemQuantity = new OrderItemQuantity();
        orderItemQuantity.setId(key);
        orderItemQuantity.setOrder(order);
        orderItemQuantity.setOrderedItem(orderedItem);
        orderItemQuantity.setQuantity(quantity);

        if(order.getItemsAndQuantities() == null){
            order.setItemsAndQuantities(new HashSet<>());
        }
        order.getItemsAndQuantities().add(orderItemQuantity);

        orderItemQuantityRepository.save(orderItemQuantity);
    }

    private Status getStatusById(long id){
        return statusRepository.findById(id).orElseThrow(() -> new NotFoundException("Order status not found!"));
    }

    private OrderedItem saveItem(OrderItemDTO dto){
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setItem(getItemById(dto.getItemId()));
        orderedItem.setPizzaSpecification(getPizzaSpecificationById(dto.getPizzaSpecification().getId()));
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

    private List<Ingredient> extractIngredients(OrderItemDTO dto){
        return dto.getIngredients().stream()
                .map(i -> getIngredientById(i.getId()))
                .toList();
    }
    private PizzaSpecification getPizzaSpecificationById(long id){
        return pizzaSpecificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pizza specification not found"));
    }

    public List<OrderResponseDTO> getOrdersForUser(long uid) {
        User u = getUserById(uid);
        return u.getOrders().stream().map(o->modelMapper.map(o, OrderResponseDTO.class)).toList();
    }

    public OrderResponseDTO getOrderById(long oid, Long uid) {
        Order order = orderRepository.findById(oid).orElseThrow(()-> new BadRequestException("Order not found"));
        if(order.getUser().getId() != uid){
            throw new AuthorizationServiceException("User is not owner");
        }
        return modelMapper.map(order, OrderResponseDTO.class);
    }
}
