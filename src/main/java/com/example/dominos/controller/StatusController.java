package com.example.dominos.controller;

import com.example.dominos.model.dto.StatusWithoutOrdersDTO;
import com.example.dominos.model.dto.order.OrderResponseDTO;
import com.example.dominos.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class StatusController extends AbstractController{

    @Autowired
    private StatusService statusService;

    @GetMapping("/status")
    public List<StatusWithoutOrdersDTO> getAllStatuses(HttpServletRequest request){
        return statusService.getAllStatuses(getLoggedUserId(request));
    }

    @PostMapping("/orders/{oid}/status/{sid}")
    public OrderResponseDTO changeOrderStatus(@PathVariable long oid, @PathVariable long sid, HttpServletRequest request){
        return statusService.changeOrderStatus(oid,sid,getLoggedUserId(request));
    }
}
