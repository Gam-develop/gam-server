package com.gam.api.service.magazine;

import com.gam.api.dto.magazine.response.MagazineDetailResponseDTO;
import com.gam.api.dto.magazine.response.MagazineResponseDTO;

public interface MagazineService {
    MagazineDetailResponseDTO getMagazineDetail(Long magazineID);
    MagazineResponseDTO getMagazines(Long userId);
}
