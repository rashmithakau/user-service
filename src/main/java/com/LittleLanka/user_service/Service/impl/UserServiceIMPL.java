package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Repositories.UserRepository;
import com.LittleLanka.user_service.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseUserDto saveUser(RequestSaveUserDTO requestSaveUserDTO) {
        // Map RequestSaveUserDTO to Entity
        User user = modelMapper.map(requestSaveUserDTO, User.class);
        User savedUser=user = userRepository.save(user); // Save the user entity to the database
        ResponseUserDto responseUserDto=modelMapper.map(user, ResponseUserDto.class);
        return responseUserDto; // Map saved entity to UserDTO
    }

    @Override
    public List<ResponseUserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            throw new RuntimeException("No users found");
        }
        return allUsers.stream()
                .map(user -> modelMapper.map(user, ResponseUserDto.class))
                .collect(Collectors.toList());
    }
}