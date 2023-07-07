package com.gam.api.service.magazine;

import com.gam.api.dto.magazine.request.MagazineScrapRequestDTO;
import com.gam.api.dto.magazine.response.MagazineDetailResponseDTO;
import com.gam.api.dto.magazine.response.MagazineResponseDTO;
import com.gam.api.dto.magazine.response.MagazineScrapResponseDTO;
import com.gam.api.dto.magazine.response.MagazineScrapsResponseDTO;

public interface MagazineService {
    MagazineResponseDTO getMagazines(Long userId);
    MagazineDetailResponseDTO getMagazineDetail(Long magazineID);
    MagazineScrapsResponseDTO getMagazineScraps(Long userId);
    MagazineResponseDTO getPopularMagazines(Long userId);
    MagazineScrapResponseDTO scrapMagazine(Long userId, MagazineScrapRequestDTO magazineScrapRequestDTO);
}
