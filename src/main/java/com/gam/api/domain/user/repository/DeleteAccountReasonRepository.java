package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.DeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteAccountReasonRepository extends JpaRepository<DeleteAccountReason, Long> {
}
