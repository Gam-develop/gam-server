package com.gam.api.repository;

import com.gam.api.entity.DeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteAccountReasonRepository extends JpaRepository<DeleteAccountReason, Long> {
}