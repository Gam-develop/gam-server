package com.gam.api.domain.magazine.service;

import com.gam.api.domain.magazine.dto.request.MagazineScrapRequestDTO;


import com.gam.api.domain.magazine.dto.response.MagazineDetailResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineScrapResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineScrapsResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineSearchResponseDTO;
import java.util.List;

public interface MagazineService {
    MagazineResponseDTO getMagazines(Long userId);
    MagazineDetailResponseDTO getMagazineDetail(Long magazineID, Long userId);
    MagazineScrapsResponseDTO getMagazineScraps(Long userId);
    MagazineResponseDTO getPopularMagazines(Long userId);
    MagazineScrapResponseDTO scrapMagazine(Long userId, MagazineScrapRequestDTO magazineScrapRequestDTO);
    List<MagazineSearchResponseDTO> searchMagazine(String keyword);
}
