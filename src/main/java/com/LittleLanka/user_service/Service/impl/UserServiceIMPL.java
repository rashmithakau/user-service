package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Repositories.UserRepository;
import com.LittleLanka.user_service.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepository userRepository; // Fixed proper naming convention

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO saveUser(RequestSaveUserDTO requestSaveUserDTO) {
        User user = modelMapper.map(requestSaveUserDTO, User.class); // Mapping Request DTO to Entity
        userRepository.save(user); // Save User in DB
        return modelMapper.map(user, UserDTO.class); // Convert Entity back to Response DTO
    }
}