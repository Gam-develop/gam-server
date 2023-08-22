package com.gam.api.service.admin;

import com.gam.api.dto.admin.magazine.request.MagazineCreateRequestDTO;
import com.gam.api.dto.admin.magazine.request.MagazineEditRequestDTO;
import com.gam.api.dto.admin.magazine.response.MagazineListResponseDTO;
import com.gam.api.entity.Magazine;

import java.util.List;

public interface AdminService {
    List<MagazineListResponseDTO> getMagazines();
    void deleteMagazine(Long magazineId);
    void createMagazine(MagazineCreateRequestDTO request);
    void editMagazine(Long magazineId, MagazineEditRequestDTO request);
}
