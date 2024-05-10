package com.gam.api.domain.block.repository;

import com.gam.api.domain.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Block findByUserIdAndTargetId(Long userId, Long targetId);
}
