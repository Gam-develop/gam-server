package com.gam.api.repository;

import com.gam.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long userId);
    boolean existsByUserName(String userName);
    List<User> findTop5ByOrderByScrapCountDesc();
    List<User> findAllByIdNotOrderBySelectedFirstAtDesc(Long id);
    Optional<User> findByUserName(String userName);
}
