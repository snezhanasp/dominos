package com.example.dominos.service;

import com.example.dominos.model.dto.address.AddressWithoutUserDTO;
import com.example.dominos.model.dto.user.*;
import com.example.dominos.model.entities.User;
import com.example.dominos.model.exceptions.BadRequestException;
import com.example.dominos.model.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService extends AbstractService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserWithoutPassDTO login(LoginDTO loginDto) {
        //validate email and password
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        if (!emailIsValid(email) || !passwordIsValid(password)){
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

    public UserWithoutPassDTO getById(long id) {
        User user = getUserById(id);
        UserWithoutPassDTO dto = modelMapper.map(user,UserWithoutPassDTO.class);
        dto.setAddresses(user.getAddresses().stream().map(a -> modelMapper.map(a, AddressWithoutUserDTO.class)).collect(Collectors.toList()));
        return modelMapper.map(user,UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO register(RegisterDTO registerDTO) {
        //validate data
        validateRegisterData(registerDTO);
        //check if user with email exists
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            throw new BadRequestException("Email already registered!");
        }
        //create new user
        User newUser = modelMapper.map(registerDTO, User.class);
        newUser.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        //save in db
        userRepository.save(newUser);
        return modelMapper.map(newUser,UserWithoutPassDTO.class);
    }

    private boolean passwordIsValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,20}$";
        //1 digit, 1 lowercase letter, 1 uppercase letter, 1 special character, 8-20 characters
        return password.matches(regex);
    }

    private boolean emailIsValid(String email) {
        String regex = "^[a-zA-Z0-9_+&*\\-]+(?:\\.[a-zA-Z0-9_+&*\\-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
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
        if (!emailIsValid(registerDTO.getEmail())){
            throw new BadRequestException("Email not valid!");
        }
        if (!passwordIsValid(password)){
            throw new BadRequestException("Password not valid!");
        }
        if (!password.equals(confirmPassword)){
            throw new BadRequestException("Passwords do not match!");
        }
    }

    public UserWithoutPassDTO edit(long id, EditProfileDTO editProfileDTO) {
        User user = getUserById(id);
        user.setFirstName(editProfileDTO.getFirstName());
        user.setLastName(editProfileDTO.getLastName());
        user.setPhone(editProfileDTO.getPhone());
        userRepository.save(user);
        return modelMapper.map(user, UserWithoutPassDTO.class);
    }

    public UserWithoutPassDTO changePassword(long id, ChangePassDTO changePassDTO) {
        String password = changePassDTO.getPassword();
        String newPassword = changePassDTO.getNewPassword();
        String confirmPassword = changePassDTO.getConfirmPassword();
        //validate data
        if (!passwordIsValid(password)){
            throw new BadRequestException("Wrong password!");
        }
        if (!newPassword.equals(confirmPassword)){
            throw new BadRequestException("Passwords do not match!");
        }
        if (!passwordIsValid(newPassword)){
            throw new BadRequestException("New password is not valid!");
        }
        //check if user id exists
        User user = getUserById(id);
        //check if entered password matches the current one
        if (!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new UnauthorizedException("Wrong credentials!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return modelMapper.map(user, UserWithoutPassDTO.class);
    }

    public boolean delete(long id, LoginDTO dto) {
        //validate email and password
        String email = dto.getEmail();
        String password = dto.getPassword();
        if (!emailIsValid(email) || !passwordIsValid(password)){
            throw new BadRequestException("Wrong credentials!");
        }
        //check if user id exists
        User user = getUserById(id);
        //check if password matches
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials!");
        }
        user.setFirstName("deleted at " + LocalDateTime.now());
        user.setLastName("deleted at " + LocalDateTime.now());
        user.setEmail("deleted at " + LocalDateTime.now());
        user.setPhone("deleted at " + LocalDateTime.now());
        userRepository.save(user);
        return true;
    }
}
