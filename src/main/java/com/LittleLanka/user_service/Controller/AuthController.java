package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.AuthRequest;
import com.LittleLanka.user_service.DTOs.AuthResponse;
import com.LittleLanka.user_service.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest.getEmail(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}