package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save-user")
    public ResponseEntity<UserDTO> saveUser(@RequestBody RequestSaveUserDTO requestSaveUserDTO) {
        UserDTO userDTO = userService.saveUser(requestSaveUserDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED); // Return status 201
    }
}