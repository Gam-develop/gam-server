package com.gam.api.service.admin;


import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.manager.magazine.response.MagazineListResponseDTO;

import com.gam.api.repository.MagazineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MagazineRepository magazineRepository;

    @Override
    public List<MagazineListResponseDTO> getMagazines() {
        return magazineRepository.findAllByOrderByModifiedAtDescCreatedAtDesc().stream()
                .map(MagazineListResponseDTO::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMagazine(Long magazineId) {
        magazineRepository.findById(magazineId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_MAGAZINE.getMessage()));

        magazineRepository.deleteById(magazineId);
    }


}
