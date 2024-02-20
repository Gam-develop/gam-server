package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.UserDeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeleteAccountReasonRepository extends JpaRepository<UserDeleteAccountReason, Long> {
}
