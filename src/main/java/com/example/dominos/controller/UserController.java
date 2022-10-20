package com.example.dominos.controller;

import com.example.dominos.model.dto.user.*;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.UnauthorizedException;
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
            //change state
            session.setAttribute("LOGGED",true);
            session.setAttribute("USER_ID",result.getId());
            //return user data
            return result;
        } else {
            throw new BadRequestException("Wrong credentials!");
        }
    }

    @GetMapping("/users/{id}")
    public UserWithoutPassDTO getById(@PathVariable long id){
        return userService.getById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithoutPassDTO register(@RequestBody RegisterDTO registerDTO, HttpSession session){
        UserWithoutPassDTO dto = userService.register(registerDTO);
        session.setAttribute("LOGGED",true);
        session.setAttribute("USER_ID",dto.getId());
        return dto;
    }

    @PutMapping("/users")
    public UserWithoutPassDTO edit(@RequestBody EditProfileDTO editProfileDTO, HttpSession session){
        //check session?
        if (session.getAttribute("LOGGED").equals(true)){
            return userService.edit((Long) session.getAttribute("USER_ID"), editProfileDTO);
        }
        throw new UnauthorizedException("User not logged!");
    }

    @PutMapping("/users/pass")
    public UserWithoutPassDTO changePassword(@RequestBody ChangePassDTO changePassDTO, HttpSession session){
        if (session.getAttribute("LOGGED").equals(true)){
            return userService.changePassword((long) session.getAttribute("USER_ID"), changePassDTO);
        }
        throw new UnauthorizedException("User not logged!");
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session){
        if (session.getAttribute("LOGGED").equals(true)){
            session.invalidate();
        }
        throw new UnauthorizedException("User not logged!");
    }

    @DeleteMapping("/users")
    public void delete(@RequestBody LoginDTO dto, HttpSession session){
        if (session.getAttribute("LOGGED").equals(true)){
            userService.delete((long) session.getAttribute("USER_ID"), dto);
            return;
        }
        throw new UnauthorizedException("User not logged!");
    }

}
