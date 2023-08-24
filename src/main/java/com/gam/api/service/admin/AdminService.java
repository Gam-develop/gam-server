package com.gam.api.service.admin;

import com.gam.api.dto.admin.magazine.request.MagazineRequestDTO;
import com.gam.api.dto.admin.magazine.response.MagazineListResponseDTO;

import java.util.List;

public interface AdminService {
    List<MagazineListResponseDTO> getMagazines();
    void deleteMagazine(Long magazineId);
    void createMagazine(MagazineRequestDTO request);
    void editMagazine(Long magazineId, MagazineRequestDTO request);
}
