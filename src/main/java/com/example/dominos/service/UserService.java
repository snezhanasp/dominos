package com.example.dominos.service;

import com.example.dominos.model.dto.user.*;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.NotFoundException;
import com.example.dominos.model.exceptions.UnauthorizedException;
import com.example.dominos.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserWithoutPassDTO login(LoginDTO loginDto) {
        //validate email and password
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        if (!validateEmail(email) || !validatePassword(password)){
            throw new BadRequestException("Wrong credentials!");
        }

        //check if user with email exists
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            User u = user.get();
            if (bCryptPasswordEncoder.matches(password,u.getPassword())){
                return modelMapper.map(u,UserWithoutPassDTO.class);
            }
        }
        throw new UnauthorizedException("Wrong credentials!");
    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$";
        //1 digit, 1 lowercase letter, 1 uppercase letter, 1 special character, 8-20 characters
        return password.matches(regex);
    }

    private boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*\\-]+(?:\\.[a-zA-Z0-9_+&*\\-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
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

    public UserWithoutPassDTO register(RegisterDTO registerDTO) {
        //validate data
        validateRegisterData(registerDTO);
        //check if user with email exists
        Optional<User> user = userRepository.findByEmail(registerDTO.getEmail());
        if (user.isPresent()){
            throw new BadRequestException("Email already registered!");
        }
        //create new user
        User newUser = modelMapper.map(registerDTO, User.class);
        newUser.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(newUser);
        return modelMapper.map(newUser,UserWithoutPassDTO.class);
    }

    private void validateRegisterData(RegisterDTO registerDTO) {
        String password = registerDTO.getPassword();
        String confirmPassword = registerDTO.getConfirmPassword();
        if (registerDTO.getFirstName().length() <= 1){
            throw new BadRequestException("First name is too short");
        }
        if (registerDTO.getLastName().length() <= 1){
            throw new BadRequestException("Last name is too short");
        }
        if (!validateEmail(registerDTO.getEmail())){
            throw new BadRequestException("Email not valid!");
        }
        if (!validatePassword(password)){
            throw new BadRequestException("Password not valid!");
        }
        if (!password.equals(confirmPassword)){
            throw new BadRequestException("Passwords do not match!");
        }
    }

    public UserWithoutPassDTO edit(long id, EditProfileDTO editProfileDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setFirstName(editProfileDTO.getFirstName());
            user.setLastName(editProfileDTO.getLastName());
            user.setPhone(editProfileDTO.getPhone());
            userRepository.save(user);
            return modelMapper.map(user, UserWithoutPassDTO.class);
        }
        throw new NotFoundException("No user with id " + id);
    }

    public UserWithoutPassDTO changePassword(long id, ChangePassDTO changePassDTO) {
        String password = changePassDTO.getPassword();
        String newPassword = changePassDTO.getNewPassword();
        String confirmPassword = changePassDTO.getConfirmPassword();
        //validate data
        if (!validatePassword(password)){
            throw new BadRequestException("Wrong password!");
        }
        if (!newPassword.equals(confirmPassword)){
            throw new BadRequestException("Passwords do not match!");
        }
        if (!validatePassword(newPassword)){
            throw new BadRequestException("New password is not valid!");
        }
        //check if user id exists
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            //check if current password matches
            if (bCryptPasswordEncoder.matches(password,user.getPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(newPassword));
                userRepository.save(user);
                return modelMapper.map(user, UserWithoutPassDTO.class);
            }
            throw new UnauthorizedException("Wrong credentials!");
        }
        throw new NotFoundException("No user with id " + id);
    }

    public void delete(long id, LoginDTO dto) {
        //validate email and password
        String email = dto.getEmail();
        String password = dto.getPassword();
        if (!validateEmail(email) || !validatePassword(password)){
            throw new BadRequestException("Wrong credentials!");
        }
        //check if user id exists
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
                user.setFirstName("deleted at " + LocalDateTime.now());
                user.setLastName("deleted at " + LocalDateTime.now());
                user.setEmail("deleted at " + LocalDateTime.now());
                user.setPhone("deleted at " + LocalDateTime.now());
                userRepository.save(user);
            }
        } else {
            throw new NotFoundException("No user with id " + id);
        }
    }
}
