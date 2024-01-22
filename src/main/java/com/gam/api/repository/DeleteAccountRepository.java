package com.gam.api.repository;

import com.gam.api.entity.DeleteAccountReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteAccountRepository extends JpaRepository<DeleteAccountReason, Long> {
}
