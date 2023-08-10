package com.gam.api.service.manager;


import com.gam.api.dto.manager.magazine.response.MagazineListResponseDTO;

import com.gam.api.repository.MagazineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final MagazineRepository magazineRepository;

    @Override
    public List<MagazineListResponseDTO> getMagazines() {
        return magazineRepository.findAllByOrderByModifiedAtDescCreatedAtDesc().stream()
                .map(MagazineListResponseDTO::of)
                .collect(Collectors.toList());
    }
}
