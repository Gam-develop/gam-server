package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.UserDeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserDeleteAccountReasonRepository extends JpaRepository<UserDeleteAccountReason, Long> {


    List<UserDeleteAccountReason> findByCreatedAtBefore(LocalDateTime createdAt);
}
