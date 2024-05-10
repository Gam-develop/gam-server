package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
