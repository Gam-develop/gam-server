package com.gam.api.domain.magazine.repository;

import com.gam.api.domain.magazine.dto.query.MagazineWithScrapQueryDTO;
import com.gam.api.domain.magazine.entity.Magazine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    Optional<Magazine> getMagazineById(Long magazineId);

    @Query("SELECT new com.gam.api.domain.magazine.dto.query.MagazineWithScrapQueryDTO(" +
            "m.id, " +
            "m.thumbNail, " +
            "m.magazineTitle, " +
            "m.interviewPerson, " +
            "m.viewCount, " +
            "EXISTS (SELECT 1 FROM MagazineScrap ms WHERE ms.magazine = m AND ms.user.id = :userId AND ms.status = true)) " +
            "FROM Magazine m ORDER BY m.createdAt DESC")
    List<MagazineWithScrapQueryDTO> findMagazinesByOrderByCreatedAtDesc(@Param("userId") Long userId);

    @Query("SELECT new com.gam.api.domain.magazine.dto.query.MagazineWithScrapQueryDTO(" +
            "m.id, " +
            "m.thumbNail, " +
            "m.magazineTitle, " +
            "m.interviewPerson, " +
            "m.viewCount, " +
            "EXISTS (SELECT 1 FROM MagazineScrap ms WHERE ms.magazine = m AND ms.user.id = :userId AND ms.status = true)) " +
            "FROM Magazine m ORDER BY m.viewCount DESC")
    List<MagazineWithScrapQueryDTO> findTopMagazinesWithScrapStatus(@Param("userId") Long userId, Pageable pageable);

    List<Magazine> findAllByOrderByModifiedAtDescCreatedAtDesc();
    @Query("SELECT m FROM Magazine m WHERE LOWER(m.interviewPerson) LIKE LOWER(CONCAT('%', :interviewPersonKeyword, '%')) OR LOWER(m.magazineTitle) LIKE LOWER(CONCAT('%', :magazineTitleKeyword, '%')) ORDER BY m.createdAt DESC")
    List<Magazine> finAllByKeyword(
            @Param("interviewPersonKeyword") String userNameKeyWord,
            @Param("magazineTitleKeyword") String titleNameKeyWord
    );
}

