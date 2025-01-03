package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestLoginDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;

import java.util.List;

public interface UserService {
    ResponseUserDto saveUser(RequestSaveUserDTO requestSaveUserDTO);  //saveuser
    List<ResponseUserDto> getAllUsers();  //get all users

    void updatePassword(Long userId, String newPassword);  //update user password

    ResponseUserDto getUserById(Long userId);

    ResponseUserDto getUserByUserNamPwd(RequestLoginDto requestLoginDto);
}