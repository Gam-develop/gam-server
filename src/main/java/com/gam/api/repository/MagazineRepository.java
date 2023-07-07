package com.gam.api.repository;

import com.gam.api.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> getMagazineById(Long magazineId);
    List<Magazine> findTop4ByOrderByCreatedAtDesc();
}
