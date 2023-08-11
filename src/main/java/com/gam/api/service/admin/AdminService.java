package com.gam.api.service.admin;

import com.gam.api.dto.admin.magazine.response.MagazineListResponseDTO;

import java.util.List;

public interface AdminService {
    List<MagazineListResponseDTO> getMagazines();
    void deleteMagazine(Long magazineId);
}
