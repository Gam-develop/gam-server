package com.gam.api.repository;

import com.gam.api.entity.UserScrap;
import com.gam.api.entity.UserTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    void deleteAllByUser_id(Long userId);
}
