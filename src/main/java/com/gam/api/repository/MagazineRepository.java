package com.gam.api.repository;

import com.gam.api.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> getMagazineById(Long magazineId);
    List<Magazine> findTop4ByOrderByCreatedAtDesc();
    List<Magazine> findTop3ByOrderByViewCountDesc();

    @Query(value = "SELECT m FROM Magazine  m WHERE m.interviewPerson LIKE %:keyword% OR m.magazineTitle LIKE %:keyword% ORDER BY m.createdAt DESC")
    List<Magazine> findAllSearch(@Param("keyword") String keyword);
}
