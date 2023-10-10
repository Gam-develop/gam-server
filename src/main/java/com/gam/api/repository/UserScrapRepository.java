package com.gam.api.repository;

import com.gam.api.entity.UserScrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserScrapRepository extends JpaRepository<UserScrap, Long> {
    UserScrap findByUser_idAndTargetId(Long userId, Long targetId);
    List<UserScrap> getAllByUser_idAndStatusOrderByCreatedAtDesc(Long userId, boolean status);
}
