package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.AuthProvider;
import com.gam.api.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, String> {
    AuthProvider searchAuthProviderById(String id);
    Optional<AuthProvider> searchAuthProviderByUser(User user);
    void deleteAllByUserId(Long userId);
}
