package com.gam.api.repository;

import com.gam.api.entity.Block;
import com.gam.api.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findByUserIdAndTargetId(Long userId, Long targetId);
}
