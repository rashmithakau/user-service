package com.LittleLanka.user_service.Controller;

import com.LittleLanka.user_service.DTOs.RoleDTO;
import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.Entities.Role;
import com.LittleLanka.user_service.Entities.Status;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Repositories.RoleRepository;
import com.LittleLanka.user_service.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* ==========================
 *  SERVICE LAYER
 * ========================== */
@Service
class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // GET ALL USERS
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // SAVE / CREATE NEW USER
    public UserDTO createUser(UserDTO userDTO) {
        // Build User entity from DTO without password encoding
        User newUser = User.builder()
                .userName(userDTO.getUserName())
                .password(userDTO.getPassword())
                .status(userDTO.getStatus() == null ? Status.ACTIVE : Status.valueOf(userDTO.getStatus()))
                .build();

        // Find or create roles
        Set<Role> roleEntities = userDTO.getRoles() == null ? Set.of() : userDTO.getRoles().stream()
                .map(r -> roleRepository.findByRoleName(r.getRoleName())
                        .orElseGet(() -> roleRepository.save(new Role(null, r.getRoleName(), r.getPermissions(), null))))
                .collect(Collectors.toSet());

        newUser.setRoles(roleEntities);
        User savedUser = userRepository.save(newUser);

        // Return the saved user as DTO
        return mapToDTO(savedUser);
    }

    /* HELPER: Convert User Entity -> UserDTO */
    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .password(user.getPassword()) // Typically, do NOT expose passwords in responses
                .status(user.getStatus().name())
                .roles(user.getRoles().stream()
                        .map(role -> new RoleDTO(role.getRoleId(), role.getRoleName(), role.getPermissions()))
                        .collect(Collectors.toSet()))
                .build();
    }
}

/* ==========================
 *  CONTROLLER LAYER
 * ========================== */
@RestController
@RequestMapping("/users")
class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    // 1) GET ENDPOINT: /users
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 2) POST ENDPOINT: /user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }
}

