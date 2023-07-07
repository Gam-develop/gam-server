package com.gam.api.service.magazine;

import com.gam.api.dto.magazine.response.MagazineDetailResponseDTO;

public interface MagazineService {
    MagazineDetailResponseDTO getMagazineDetail(Long magazineID);
}
