package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    void deleteAllByUser_id(Long userId);
}
