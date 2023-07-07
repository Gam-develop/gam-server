package com.gam.api.service.magazine;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.magazine.response.MagazineDetailResponseDTO;
import com.gam.api.dto.magazine.response.MagazineResponseDTO;
import com.gam.api.dto.magazine.response.MagazineScrapsResponseDTO;
import com.gam.api.entity.MagazineScrap;
import com.gam.api.entity.User;
import com.gam.api.entity.superclass.TimeStamped;
import com.gam.api.repository.MagazineRepository;
import com.gam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;

@RequiredArgsConstructor
@Service
public class MagazineServiceImpl implements MagazineService {
    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;

    @Override
    public MagazineResponseDTO getMagazines(Long userId) {
        val user = findUser(userId);
        val magazineScrapList = user.getMagazineScraps().stream()
                .map(MagazineScrap::getMagazineId)
                .toList();

        val magazineList = magazineRepository.findTop4ByOrderByCreatedAtDesc();

        return MagazineResponseDTO.of(magazineList, magazineScrapList);
    }

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

    public MagazineScrapsResponseDTO getMagazineScraps(Long userId) {
        val user = findUser(userId);

        val magazineScraps = user.getMagazineScraps();

        magazineScraps.sort(Comparator.comparing(TimeStamped::getCreatedAt).reversed());

        val magazineList = magazineScraps.stream()
                .map(MagazineScrap::getMagazine)
                .toList();

        return MagazineScrapsResponseDTO.of(magazineList);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }
}
