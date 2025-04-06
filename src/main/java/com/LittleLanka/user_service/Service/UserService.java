package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestLoginDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
import com.LittleLanka.user_service.Entities.enums.UserStatus;

import com.LittleLanka.user_service.DTOs.response.ResponseUserWithPermissionsDto;
import com.LittleLanka.user_service.Entities.enums.UserStatus;


import java.util.List;

public interface UserService {
    ResponseUserDto saveUser(RequestSaveUserDTO requestSaveUserDTO);
    List<ResponseUserDto> getAllUsers();  //get all users

    void updatePassword(Long userId, String newPassword);  //update user password

    ResponseUserDto getUserById(Long userId);

    void updateUserStatus(Long userId, String status);

    ResponseUserDto getUserByUserNamPwd(RequestLoginDto requestLoginDto);

    ResponseUserWithPermissionsDto getUserWithPermissionsById(Long userId);

    List<ResponseUserDto> getUsersByStatus(UserStatus status);

    ResponseUserDto saveOutletUser(RequestSaveUserDTO requestSaveUserDTO);

    void updatePhoneNumber(Long userId, String newPhoneNumber);

    ResponseUserDto saveStaffUser(RequestSaveUserDTO requestSaveUserDTO);

    List<ResponseUserDto> getOutletUsers();}