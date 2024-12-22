package com.LittleLanka.user_service.Service;

import com.LittleLanka.user_service.DTOs.UserDTO;
import com.LittleLanka.user_service.Entities.Role;
import com.LittleLanka.user_service.Entities.Status;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Repositories.RoleRepository;
import com.LittleLanka.user_service.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    // Create a new user
    public UserDTO saveUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        Set<Role> roles = user.getRoles().stream()
                .map(roleDTO -> roleRepository.findByRoleName(roleDTO.getRoleName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleDTO.getRoleName())))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    // Get user by ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return modelMapper.map(user, UserDTO.class);
    }

    // Update user details
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }
            existingUser.setEmail(userDTO.getEmail());
        }

        if (userDTO.getUserName() != null) {
            existingUser.setUserName(userDTO.getUserName());
        }

        if (userDTO.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            Set<Role> roles = userDTO.getRoles().stream()
                    .map(roleDTO -> roleRepository.findByRoleName(roleDTO.getRoleName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleDTO.getRoleName())))
                    .collect(Collectors.toSet());
            existingUser.setRoles(roles);
        }

        if (userDTO.getStatus() != null) {
            existingUser.setStatus(Status.valueOf(userDTO.getStatus()));
        }

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    // Deactivate user
    public void deactivateUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        existingUser.setStatus(Status.INACTIVE);
        userRepository.save(existingUser);
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}