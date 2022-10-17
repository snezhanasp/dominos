package com.example.dominos.service;

import com.example.dominos.model.dto.user.LoginDTO;
import com.example.dominos.model.dto.user.UserWithoutPassDTO;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.exceptions.UnauthorizedException;
import com.example.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UserWithoutPassDTO login(LoginDTO loginDto) {
        //validate email and password
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
//        if (!validateEmail(email) || !validatePassword(password)){
//            throw new BadRequestException("Wrong credentials!");
//        }
        //check if user with email and password exists
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            //TODO bCrypt password
            return modelMapper.map(user.get(),UserWithoutPassDTO.class);
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

        return password.matches(regex);
    }

    private boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]" +
                "{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

        return email.matches(regex);

    }

    public UserWithoutPassDTO getById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return modelMapper.map(user.get(),UserWithoutPassDTO.class);
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
