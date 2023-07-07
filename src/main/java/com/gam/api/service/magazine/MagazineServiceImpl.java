package com.gam.api.service.magazine;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.magazine.response.MagazineDetailResponseDTO;
import com.gam.api.repository.MagazineRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class MagazineServiceImpl implements MagazineService {
    private final MagazineRepository magazineRepository;
    @Override
    public MagazineDetailResponseDTO getMagazineDetail(Long magazineId) {
        val magazine = magazineRepository.getMagazineById(magazineId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_MAGAZINE.getMessage()));

        val magazinePhotos = magazine.getMagazinePhotos();
        val magazineQuestions = magazine.getQuestions();

        return MagazineDetailResponseDTO.of(
                magazine.getId(),
                magazinePhotos,
                magazine.getIntroduction(),
                magazineQuestions
        );
    }
}
