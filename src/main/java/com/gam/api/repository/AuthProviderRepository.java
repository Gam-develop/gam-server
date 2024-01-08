package com.gam.api.repository;

import com.gam.api.entity.AuthProvider;
import com.gam.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, String> {
    AuthProvider searchAuthProviderById(String id);
    Optional<AuthProvider> searchAuthProviderByUser(User user);
}
