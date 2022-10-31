package com.example.dominos.service;

import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.entities.Order;
import com.example.dominos.model.entities.Status;
import com.example.dominos.model.exceptions.UnauthorizedException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatusService extends AbstractService{

    public List<Status> getAllStatuses(long uid) {
        //check if user is staff
        if (!getUserById(uid).isStaff()){
            throw new UnauthorizedException("You do not have permission to access this information!");
        }
        return statusRepository.findAll();
    }

    public OrderResponseDTO changeOrderStatus(long oid, long sid, long uid) {
        //check if user is staff
        if (!getUserById(uid).isStaff()){
            throw new UnauthorizedException("You do not have permission to access this information!");
        }
        Order order = getOrderById(oid);
        order.setStatus(getStatusById(sid));
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Scheduled(cron = "0 0 1 * * *")// 1 o'clock every night
    public void invalidateOrder(){
        //get orders from yesterday
        List<Order> orders = orderRepository.findAllByOrderedAtIsAfter(LocalDateTime.now().minusDays(2));
        if (!orders.isEmpty()){
            for (Order order : orders) {
                //get not delivered orders and set status to canceled
                if (order.getStatus().getId() == ACCEPTED || order.getStatus().getId() == READY_FOR_DELIVERY){
                    order.setStatus(getStatusById(CANCELED));
                    orderRepository.save(order);
                }
            }
        }
    }
}
