package com.example.dominos.service;

import com.example.dominos.model.dto.PaymentMethodWithoutOrdersDTO;
import com.example.dominos.model.dto.StatusWithoutOrdersDTO;
import com.example.dominos.model.dto.address.AddressWithoutUserDTO;
import com.example.dominos.model.dto.ingredient.IngredientWithoutItemsAndTypeDTO;
import com.example.dominos.model.dto.item.ItemInfoDTO;
import com.example.dominos.model.dto.order.CreateOrderDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.dto.item.CartItemDTO;
import com.example.dominos.model.entities.Ingredient;
import com.example.dominos.model.entities.Order;
import com.example.dominos.model.entities.OrderItemQuantity;
import com.example.dominos.model.entities.OrderedItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService extends AbstractService{

    @Transactional
    public OrderResponseDTO createOrder(CreateOrderDTO dto, Map<CartItemDTO, Integer> cart, long uid, long aid) {
        //create order and save in order repo
        Order order = modelMapper.map(dto, Order.class); //price, time, payment, status
        order.setStatus(getStatusById(dto.getStatusId()));
        order.setPayment(getPaymentMethodById(dto.getPaymentMethodId()));
        order.setUser(getUserById(uid));
        order.setAddress(getAddressById(aid));
        orderRepository.save(order);

        Map<ItemInfoDTO, Integer> items = new HashMap<>();

        //create ordered items and save in ordered items repo
        for (Map.Entry<CartItemDTO, Integer> entry : cart.entrySet()) {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setItem(getItemById(entry.getKey().getItemId()));
            orderedItem.setIngredients(new ArrayList<>());
            List<IngredientWithoutItemsAndTypeDTO> ingredientsDTO = entry.getKey().getIngredients();
            for (IngredientWithoutItemsAndTypeDTO ingredient : ingredientsDTO) {
                orderedItem.getIngredients().add(modelMapper.map(ingredient, Ingredient.class));
            }
            //TODO check if pizza spec is null
            orderedItem.setPizzaSpecification(getPizzaSpecById(entry.getKey().getPizzaSpecificationId()));
            orderedItemRepository.save(orderedItem);

            //save to quantities repo (many to many with pivot)
            OrderItemQuantity quantity = new OrderItemQuantity();
            quantity.setOrder(order);
            quantity.setOrderedItem(orderedItem);
            quantity.setQuantity(entry.getValue());
            orderItemQuantityRepository.save(quantity);


            items.put(modelMapper.map(orderedItem, ItemInfoDTO.class),entry.getValue());
        }

        OrderResponseDTO orderResponseDTO = modelMapper.map(order,OrderResponseDTO.class);
        orderResponseDTO.setAddress(modelMapper.map(order.getAddress(), AddressWithoutUserDTO.class));
        orderResponseDTO.setPaymentMethod(modelMapper.map(order.getPayment(), PaymentMethodWithoutOrdersDTO.class));
        orderResponseDTO.setStatus(modelMapper.map(order.getStatus(), StatusWithoutOrdersDTO.class));
        orderResponseDTO.setItems(items);
        return orderResponseDTO;
    }
}
