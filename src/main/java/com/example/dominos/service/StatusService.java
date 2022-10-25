package com.example.dominos.service;

import com.example.dominos.model.dto.StatusWithoutOrdersDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.model.entities.Order;
import com.example.dominos.model.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService extends AbstractService{


    public List<StatusWithoutOrdersDTO> getAllStatuses(long uid) {
        //check if user is staff
        if (!getUserById(uid).isStaff()){
            throw new UnauthorizedException("You do not have permission to access this information!");
        }
        return statusRepository.findAll().stream()
                .map(s -> modelMapper.map(s, StatusWithoutOrdersDTO.class))
                .toList();
    }

    public OrderResponseDTO changeOrderStatus(long oid, long sid, long uid) {
        //check if user is staff
        if (!getUserById(uid).isStaff()){
            throw new UnauthorizedException("You do not have permission to access this information!");
        }
        Order order = getOrderById(oid);
        order.setStatus(getStatusById(sid));
        return modelMapper.map(order, OrderResponseDTO.class);
    }
}
