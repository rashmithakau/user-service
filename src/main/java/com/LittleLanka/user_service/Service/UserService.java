package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;

public interface UserService {
    UserDTO saveUser(RequestSaveUserDTO requestSaveUserDTO); // Fixed method name
}