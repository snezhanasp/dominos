package com.example.dominos.controller;

import com.example.dominos.model.dto.user.*;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController extends AbstractController{
    @Autowired
    private UserService userService;

    @PostMapping("/auth")
    public UserWithoutPassDTO login(@RequestBody LoginDTO loginDto, HttpServletRequest request){
        //TODO check if already logged

        UserWithoutPassDTO result = userService.login(loginDto);
        if (result != null){
            logUser(request,result.getId());
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
    public UserWithoutPassDTO register(@RequestBody RegisterDTO registerDTO, HttpServletRequest request){
        UserWithoutPassDTO dto = userService.register(registerDTO);
        logUser(request, dto.getId());
        return dto;
    }

    @PutMapping("/users")
    public UserWithoutPassDTO edit(@RequestBody EditProfileDTO editProfileDTO, HttpServletRequest request){
        return userService.edit(getLoggedUserId(request), editProfileDTO);
    }

    @PutMapping("/users/pass")
    public UserWithoutPassDTO changePassword(@RequestBody ChangePassDTO changePassDTO, HttpServletRequest request){
        return userService.changePassword(getLoggedUserId(request), changePassDTO);
    }

    @PostMapping("/logout")
    public boolean logout(HttpServletRequest request){
        //check if logged
        getLoggedUserId(request);
        //invalidate session
        HttpSession session = request.getSession();
        session.invalidate();
        return true;
    }

    @DeleteMapping("/users")
    public boolean delete(@RequestBody LoginDTO dto, HttpServletRequest request){
        return userService.delete(getLoggedUserId(request), dto);
    }

}
