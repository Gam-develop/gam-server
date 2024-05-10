package com.gam.api.domain.admin.service;

import com.gam.api.domain.admin.dto.magazine.request.MagazineRequestDTO;
import com.gam.api.domain.admin.dto.magazine.response.MagazineDetailResponseDTO;
import com.gam.api.domain.admin.dto.magazine.response.MagazineListResponseDTO;

import java.util.List;

public interface AdminService {
    List<MagazineListResponseDTO> getMagazines();
    void deleteMagazine(Long magazineId);
    void createMagazine(MagazineRequestDTO request);
    void editMagazine(Long magazineId, MagazineRequestDTO request);
    void deleteUserAccount(Long userId);
    MagazineDetailResponseDTO getMagazineDetail(Long magazineID, Long userId);
}
