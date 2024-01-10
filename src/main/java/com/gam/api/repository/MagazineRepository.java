package com.gam.api.repository;

import com.gam.api.entity.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> getMagazineById(Long magazineId);
    List<Magazine> findMagazinesByOrderByViewCountDesc();
    List<Magazine> findTop3ByOrderByViewCountDesc();
    List<Magazine> findAllByOrderByModifiedAtDescCreatedAtDesc();
    @Query("SELECT m FROM Magazine m WHERE LOWER(m.interviewPerson) LIKE LOWER(CONCAT('%', :interviewPersonKeyword, '%')) OR LOWER(m.magazineTitle) LIKE LOWER(CONCAT('%', :magazineTitleKeyword, '%')) ORDER BY m.createdAt DESC")
    List<Magazine> finAllByKeyword(
            @Param("interviewPersonKeyword") String userNameKeyWord,
            @Param("magazineTitleKeyword") String titleNameKeyWord
    );
}

