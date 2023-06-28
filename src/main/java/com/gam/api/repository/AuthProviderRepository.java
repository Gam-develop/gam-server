package com.gam.api.repository;

import com.gam.api.entity.AuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Long> {
    AuthProvider searchAuthProviderById(Long id);
}
