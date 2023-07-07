package com.gam.api.repository;

import com.gam.api.entity.MagazineScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineScrapRepository extends JpaRepository<MagazineScrap, Long> {
    MagazineScrap findByUser_IdAndMagazine_Id(Long userId, Long magazineId);
}
