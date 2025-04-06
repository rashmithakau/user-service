package com.LittleLanka.user_service.Repositories;

import com.LittleLanka.user_service.Entities.Role;
import com.LittleLanka.user_service.Entities.User;
import com.LittleLanka.user_service.Entities.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);

    User getUserByUserNameAndPassword(String userName, String password);

    boolean existsByUserNameAndPassword(String userName, String password);

    boolean existsByUserName(String userName);

    User getUserByUserName(String userName);

    List<User> findByStatus(UserStatus status);

    List<User> findByRoleIn(List<Role> roles);


}