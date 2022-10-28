package com.example.dominos.service;

import com.example.dominos.model.compositeKeys.OrderOrderedItemKey;
import com.example.dominos.model.dto.item.CartItemWithQuantityDTO;
import com.example.dominos.model.dto.order.CreateOrderDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.dto.ordered_item.OrderItemDTO;
import com.example.dominos.model.entities.*;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.exceptions.UnauthorizedException;
import com.example.dominos.model.repositories.OrderItemQuantityRepository;
import com.example.dominos.model.repositories.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService extends AbstractService{

    @Autowired
    protected PaymentMethodRepository paymentMethodRepository;
    @Autowired
    protected OrderItemQuantityRepository orderItemQuantityRepository;
    public static final int MIN_ORDER_PRICE = 10;

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO dto, Set<CartItemWithQuantityDTO> cart, long uid, long aid) {

        //create order and save in order repo
        if(cart.isEmpty()){
            throw new BadRequestException("Cart is empty!");
        }
        double totalPrice = calculatePrice(cart);
        if(totalPrice < MIN_ORDER_PRICE){
            throw new BadRequestException("Order has to be at least 10lv.");
        }
        Order order = saveOrder(dto, uid, aid, totalPrice);

        for (CartItemWithQuantityDTO cartItemDTO: cart) {
            OrderedItem orderedItem = saveItem(cartItemDTO.getItem());
            int quantity = cartItemDTO.getQuantity();
            saveOrderOrderedItemRelation(order, orderedItem, quantity);
        }

        OrderResponseDTO orderResponseDTO = modelMapper.map(order,OrderResponseDTO.class);
        orderResponseDTO.setItems(cart);
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
        OrderOrderedItemKey key = new OrderOrderedItemKey(order.getId(), orderedItem.getId());

        OrderItemQuantity orderItemQuantity = new OrderItemQuantity();
        orderItemQuantity.setId(key);
        orderItemQuantity.setOrder(order);
        orderItemQuantity.setOrderedItem(orderedItem);
        orderItemQuantity.setQuantity(quantity);
        order.getItemsAndQuantities().add(orderItemQuantity);

        orderItemQuantityRepository.save(orderItemQuantity);
    }

    private OrderedItem saveItem(OrderItemDTO dto){
        OrderedItem orderedItem = new OrderedItem();
        orderedItem.setItem(getItemById(dto.getItemId()));
        if(dto.getPizzaSpecification() != null) {
            orderedItem.setPizzaSpecification(getPizzaSpecificationById(dto.getPizzaSpecification().getId()));
        }
        orderedItem.setIngredients(extractIngredients(dto));
        orderedItemRepository.save(orderedItem);
        return orderedItem;
    }

    private Order saveOrder(CreateOrderDTO dto, long uid, long aid, double price){
        Order order = new Order();
        order.setPrice(price);
        order.setOrderedAt(LocalDateTime.now());
        order.setStatus(getStatusById(ACCEPTED));
        order.setPayment(getPaymentMethodById(dto.getPaymentMethodId()));
        order.setUser(getUserById(uid));
        order.setAddress(getAddressById(aid));
        orderRepository.save(order);
        return order;
    }
    private PaymentMethod getPaymentMethodById(long id){
        return paymentMethodRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment method not found!"));
    }

    private List<Ingredient> extractIngredients(OrderItemDTO dto){
        if(dto.getIngredients() == null){
            return null;
        }
        return dto.getIngredients().stream()
                .map(i -> getIngredientById(i.getId()))
                .toList();
    }
    private PizzaSpecification getPizzaSpecificationById(long id){
        return pizzaSpecificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pizza specification not found"));
    }

    private Ingredient getIngredientById(long id){
        return ingredientRepository.findById(id).orElseThrow(() -> new NotFoundException("Ingredient not found"));    }

    public List<OrderResponseDTO> getOrdersForUser(long uid) {
        User u = getUserById(uid);
        return u.getOrders().stream().map(o->modelMapper.map(o, OrderResponseDTO.class)).toList();
    }

    public OrderResponseDTO getOrderById(long oid, Long uid) {
        Order order = getOrderById(oid);
        if(order.getUser().getId() != uid){
            throw new UnauthorizedException("User is not owner");
        }
        return modelMapper.map(order, OrderResponseDTO.class);
    }
}
