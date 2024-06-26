package com.gam.api.domain.magazine.service;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.config.GamConfig;
import com.gam.api.domain.magazine.dto.request.MagazineScrapRequestDTO;
import com.gam.api.domain.magazine.dto.response.MagazineScrapsResponseDTO;
import com.gam.api.domain.magazine.entity.Magazine;
import com.gam.api.domain.user.entity.MagazineScrap;
import com.gam.api.domain.magazine.dto.response.MagazineDetailResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineScrapResponseDTO;
import com.gam.api.domain.magazine.dto.response.MagazineSearchResponseDTO;
import com.gam.api.domain.user.entity.User;
import com.gam.api.domain.user.entity.UserStatus;
import com.gam.api.common.entity.superclass.TimeStamped;
import com.gam.api.domain.magazine.repository.MagazineRepository;
import com.gam.api.domain.user.repository.MagazineScrapRepository;
import com.gam.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MagazineServiceImpl implements MagazineService {
    private final MagazineRepository magazineRepository;
    private final UserRepository userRepository;
    private final MagazineScrapRepository magazineScrapRepository;

    private final GamConfig gamConfig;

    @Override
    public MagazineResponseDTO getMagazines(Long userId) {
        val user = findUser(userId);
        val magazineScrapList = getMagazineScrapList(user);
        val magazineList = magazineRepository.findMagazinesByOrderByCreatedAtDesc();

        return MagazineResponseDTO.of(magazineList, magazineScrapList, gamConfig.getMagaineBaseUrl());
    }

    @Transactional
    @Override
    public MagazineDetailResponseDTO getMagazineDetail(Long magazineId, Long userId) {
        val magazine = getMagazine(magazineId);
        val user = findUser(userId);
        val magazineCount = user.getMagazineViewCount();
        val isWorkEmpty = user.getWorks().size() == 0;

        if (magazineCount == 2 && isWorkEmpty) {
            user.updateUserStatus(UserStatus.NOT_PERMITTED);
        }

        if (magazineCount < 3 && isWorkEmpty) {
            user.magazineViewCountUp();
        }

        magazine.setViewCount(magazine.getViewCount() + 1);
        return MagazineDetailResponseDTO.of(magazine, gamConfig.getMagaineBaseUrl());
    }

    @Override
    public MagazineScrapsResponseDTO getMagazineScraps(Long userId) {
        val user = findUser(userId);
        val magazineScraps = user.getMagazineScraps();
        magazineScraps.sort(Comparator.comparing(TimeStamped::getModifiedAt).reversed());

        val magazineList = magazineScraps.stream()
                .map(MagazineScrap::getMagazine)
                .toList();

        return MagazineScrapsResponseDTO.of(magazineList, gamConfig.getMagaineBaseUrl());
    }

    @Override
    public MagazineResponseDTO getPopularMagazines(Long userId) {
        val user = findUser(userId);
        val magazineScrapList = getMagazineScrapList(user);
        val magazineList = magazineRepository.findTop3ByOrderByViewCountDesc();

        return MagazineResponseDTO.of(magazineList, magazineScrapList, gamConfig.getMagaineBaseUrl());
    }

    @Transactional
    @Override
    public MagazineScrapResponseDTO scrapMagazine(Long userId, MagazineScrapRequestDTO magazineScrapRequestDTO) {
        val magazineId = magazineScrapRequestDTO.targetMagazineId();
        val magazine = getMagazine(magazineId);
        val user = findUser(userId);

        val magazineScrap = magazineScrapRepository
                .findByUser_IdAndMagazine_Id(
                        userId,
                        magazineScrapRequestDTO.targetMagazineId()
                );

        if (Objects.nonNull(magazineScrap)) {
            val status = magazineScrap.isStatus();
            validateStatusRequest(status, magazineScrapRequestDTO.currentScrapStatus());

            if (status) {
                magazine.scrapCountDown();
            } else {
                magazine.scrapCountUp();
            }

            magazineScrap.setScrapStatus(!status);

            return MagazineScrapResponseDTO.of(magazineId, magazineScrap.isStatus());
        }

        val createdMagazineScrap = magazineScrapRepository.save(MagazineScrap.builder()
                .user(user)
                .magazine(magazine)
                .build());

        return MagazineScrapResponseDTO.of(magazineId, createdMagazineScrap.isStatus());
    }

    @Override
    public List<MagazineSearchResponseDTO> searchMagazine(String keyword) {
        val magazines = magazineRepository.finAllByKeyword(keyword, keyword);
        return magazines.stream()
                .sorted(Comparator.comparingLong(Magazine::getViewCount).reversed())
                .map((magazine) -> MagazineSearchResponseDTO.of(magazine, gamConfig.getMagaineBaseUrl()))
                .collect(Collectors.toList());
    }

    private List<Long> getMagazineScrapList(User user) {
        return user.getMagazineScraps().stream()
                .map(MagazineScrap::getMagazineId)
                .toList();
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private Magazine getMagazine(Long magazineId) {
        return magazineRepository.getMagazineById(magazineId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_MAGAZINE.getMessage()));
    }

    private void validateStatusRequest(boolean status, boolean requestStatus) {
        if (status != requestStatus) {
            throw new IllegalArgumentException(ExceptionMessage.NOT_MATCH_MAGAZINE_SCARP_STATUS.getMessage());
        }
    }
}
