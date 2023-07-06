package com.gam.api.repository;

import com.gam.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long userId);
    boolean existsByUserName(String userName);
}
