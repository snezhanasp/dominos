package com.example.dominos.controller;

import com.example.dominos.model.dto.user.LoginDTO;
import com.example.dominos.model.dto.user.RegisterDTO;
import com.example.dominos.model.dto.user.UserWithoutPassDTO;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController{

    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/auth")
    public UserWithoutPassDTO login(@RequestBody LoginDTO loginDto, HttpSession session){
        //TODO check if already logged
        UserWithoutPassDTO result = userService.login(loginDto);
        if (result != null){
            session.setAttribute("LOGGED",true);
            session.setAttribute("USER_ID",result.getId());
            return result;
        } else {
            throw new BadRequestException("Wrong credentials!");
        }

    }

    @GetMapping("/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable long id){
        UserWithoutPassDTO result = userService.getById(id);
        return result;
    }




}
