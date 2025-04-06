package com.LittleLanka.user_service.Service.impl;

import com.LittleLanka.user_service.DTOs.request.RequestLoginDto;
import com.LittleLanka.user_service.DTOs.request.RequestSaveUserDTO;
import com.LittleLanka.user_service.DTOs.response.ResponseUserDto;
import com.LittleLanka.user_service.DTOs.response.ResponseUserWithPermissionsDto;
import com.LittleLanka.user_service.Entities.Permission;
import com.LittleLanka.user_service.Entities.Role;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Entities.enums.UserStatus;
import com.LittleLanka.user_service.Repositories.RoleRepository;
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
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseUserDto saveUser(RequestSaveUserDTO requestSaveUserDTO) {
        // Fetching the Role based on the roleId
        Role role = roleRepository.getReferenceById(requestSaveUserDTO.getRoleId());

        // Manually mapping the RequestSaveUserDTO to User entity
        User user = new User();
        user.setUserName(requestSaveUserDTO.getUserName());
        user.setPhoneNumber(requestSaveUserDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(requestSaveUserDTO.getPassword()));
        user.setStatus(UserStatus.ACTIVE); // New users should be ACTIVE
        user.setRole(role);
        user.setOutletID(requestSaveUserDTO.getOutletID());

        // Saving the user
        User savedUser = userRepository.save(user);

        // Manually mapping savedUser to ResponseUserDto
        ResponseUserDto responseUserDto = new ResponseUserDto();
        modelMapper.map(savedUser, responseUserDto);

        return responseUserDto;
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
    public void updateUserStatus(Long userId, String status) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));
        UserStatus s = UserStatus.valueOf(status.toUpperCase());
        u.setStatus(s);
        userRepository.save(u);
    }




    public ResponseUserWithPermissionsDto getUserWithPermissionsById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        // Extract permissions from the user's role
        List<String> permissions = user.getRole().getPermissions()
                .stream()
                .map(Permission::getPermissionName)
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

    @Override
    public List<ResponseUserDto> getUsersByStatus(UserStatus status) {
        List<User> users = userRepository.findByStatus(status);
        if (users.isEmpty()) {
            throw new RuntimeException("No users found with status " + status);
        }
        return users.stream()
                .map(user -> modelMapper.map(user, ResponseUserDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public void updatePhoneNumber(Long userId, String newPhoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID " + userId));

        user.setPhoneNumber(newPhoneNumber);
        userRepository.save(user);
    }

    @Override
    public ResponseUserDto saveStaffUser(RequestSaveUserDTO requestSaveUserDTO) {
        // Map DTO to User entity
        User user = modelMapper.map(requestSaveUserDTO, User.class);

        // Encode password
        user.setPassword(passwordEncoder.encode(requestSaveUserDTO.getPassword()));

        // Set user status to ACTIVE
        user.setStatus(UserStatus.ACTIVE);

        // Fetch "Staff" role from the database using injected RoleRepository
        Role staffRole = roleRepository.findByRoleName("Staff")
                .orElseThrow(() -> new RuntimeException("Role 'Staff' not found"));

        // Assign the role to the user
        user.setRole(staffRole);

        // Save the user
        User savedUser = userRepository.save(user);

        // Return the response DTO
        return modelMapper.map(savedUser, ResponseUserDto.class);
    }

    @Override
    public ResponseUserDto saveOutletUser(RequestSaveUserDTO requestSaveUserDTO) {
        // Map DTO to User entity
        User user = modelMapper.map(requestSaveUserDTO, User.class);

        // Encode password
        user.setPassword(passwordEncoder.encode(requestSaveUserDTO.getPassword()));

        user.setStatus(UserStatus.ACTIVE);

        Role outletRole = roleRepository.findByRoleName("Outlet")
                .orElseThrow(() -> new RuntimeException("Role 'Outlet' not found"));

        user.setRole(outletRole);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, ResponseUserDto.class);
    }

    @Override
    public List<ResponseUserDto> getOutletAndStaffUsers() {
        // Retrieve both roles from the database
        Role outletRole = roleRepository.findByRoleName("Outlet")
                .orElseThrow(() -> new RuntimeException("Role 'Outlet' not found"));
        Role staffRole = roleRepository.findByRoleName("Staff")
                .orElseThrow(() -> new RuntimeException("Role 'Staff' not found"));

        // Find users with either role
        List<User> users = userRepository.findByRoleIn(List.of(outletRole, staffRole));

        if (users.isEmpty()) {
            throw new RuntimeException("No users found with roles Outlet or Staff");
        }

        // Convert to DTOs
        return users.stream()
                .map(user -> modelMapper.map(user, ResponseUserDto.class))
                .collect(Collectors.toList());
    }

}
