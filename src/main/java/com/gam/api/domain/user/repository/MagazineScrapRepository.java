package com.gam.api.domain.user.repository;

import com.gam.api.domain.user.entity.MagazineScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineScrapRepository extends JpaRepository<MagazineScrap, Long> {
    MagazineScrap findByUser_IdAndMagazine_Id(Long userId, Long magazineId);
}
