package com.gam.api.repository;

import com.gam.api.entity.User;
import com.gam.api.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository  extends JpaRepository<UserTag, Long> {
}
