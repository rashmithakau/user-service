package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;

import java.util.List;

public interface UserService {
    ResponseUserDto saveUser(RequestSaveUserDTO requestSaveUserDTO);
    List<ResponseUserDto> getAllUsers();
}