package com.gam.api.service.manager;

import com.gam.api.dto.manager.magazine.response.MagazineListResponseDTO;

import java.util.List;

public interface ManagerService {
    List<MagazineListResponseDTO> getMagazines();
    void deleteMagazine(Long magazineId);
}
