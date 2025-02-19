package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.request.RequestLoginDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
import com.LittleLanka.user_service.DTOs.response.ResponseUserWithPermissionsDto;
import com.LittleLanka.user_service.Entities.Permission;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Entities.enums.UserStatus;
import com.LittleLanka.user_service.Repositories.UserRepository;
import com.LittleLanka.user_service.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDto saveUser(RequestSaveUserDTO requestSaveUserDTO) {

        User user = modelMapper.map(requestSaveUserDTO, User.class);

        user.setPassword(passwordEncoder.encode(requestSaveUserDTO.getPassword()));
        //
        // Save the user entity to the database
        User savedUser = userRepository.save(user);


        return modelMapper.map(savedUser, ResponseUserDto.class);
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

    @Override
    public void updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));
        // Encode the new password
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

    @Override
    public ResponseUserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        return modelMapper.map(user, ResponseUserDto.class);
    }

    @Override
    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    public ResponseUserWithPermissionsDto getUserWithPermissionsById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        // Extract permissions from the user's role
        List<String> permissions = user.getRole().getPermissions()
                .stream()
                .map(Permission::getPermissionName) // Convert Permission object to permission name
                .collect(Collectors.toList());

        // Return response with user details and permissions
        return new ResponseUserWithPermissionsDto(
                user.getUserId(),
                user.getUserName(),
                permissions
        );
    }


    @Override
    public ResponseUserDto getUserByUserNamPwd(RequestLoginDto requestLoginDto) {

        if(!userRepository.existsByUserName(requestLoginDto.getUserName())){

            throw new RuntimeException("User not found with name " + requestLoginDto.getUserName());
        }

        User user=userRepository.getUserByUserName(requestLoginDto.getUserName());
        if(!passwordEncoder.matches(requestLoginDto.getPassword(),user.getPassword())){
            throw new RuntimeException("Wrong password");
        }

        return modelMapper.map(user, ResponseUserDto.class);
    }


}
