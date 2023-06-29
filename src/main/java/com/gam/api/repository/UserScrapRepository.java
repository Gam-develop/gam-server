package com.gam.api.repository;

import com.gam.api.entity.UserScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserScrapRepository extends JpaRepository<UserScrap, Long> {
    Optional<UserScrap> findByUserScrap_idAndTargetId(Long user_id, Long target_id);
}
