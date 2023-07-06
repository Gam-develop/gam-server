package com.gam.api.service.user;

import com.gam.api.common.exception.WorkException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.common.message.ExceptionMessage.*;
import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.*;
import com.gam.api.dto.user.request.UserWorkEditRequestDTO;
import com.gam.api.entity.User;
import com.gam.api.entity.UserScrap;
import com.gam.api.entity.UserTag;
import com.gam.api.entity.Work;
import com.gam.api.repository.*;
import com.gam.api.service.s3.S3ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;
    private final WorkRepository workRepository;
    private final S3ServiceImpl s3Service;

    @Transactional
    @Override
    public UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request) {
        val targetUser = userRepository.findById(request.targetUserId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, targetUser.getId());

        if (userScrap.isPresent()) {
            chkClientAndDBStatus(userScrap.get().isStatus(), request.currentScrapStatus());

            if (userScrap.get().isStatus() == true) targetUser.scrapCountDown();
            else targetUser.scrapCountUp();

            userScrap.get().setScrapStatus(!userScrap.get().isStatus());

            return UserScrapResponseDto.of(targetUser.getId(), targetUser.getUserName(), userScrap.get().isStatus());
        }
        createUserScrap(user, targetUser.getId(), targetUser);

        return UserScrapResponseDto.of(targetUser.getId(), targetUser.getUserName(), true);
    }

    @Transactional
    @Override
    public UserExternalLinkResponseDto updateExternalLink(Long userId, UserExternalLinkRequestDto request){
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
        user.setAdditionalLink(request.externalLink());
        return UserExternalLinkResponseDto.of(user.getAdditionalLink());
    }

    @Transactional
    @Override
    public UserMyProfileResponse getMyProfile(Long userId){
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
        return UserMyProfileResponse.of(user);
    }

    @Transactional
    @Override
    public UserProfileUpdateResponseDto updateMyProfile(Long userId, UserProfileUpdateRequestDto request){
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        if (request.userInfo() != null) {
            user.setInfo(request.userInfo());
        }

        if (request.userDetail() != null) {
            user.setDetail(request.userDetail());
        }

        if (request.email() != null) {
            user.setEmail(request.email());
        }

        if (request.tags() != null) {
            val newTags = request.tags();
            val tags = tagRepository.findAll();

            userTagRepository.deleteAllByUser_id(userId);

            for (Integer tag: newTags) {
                userTagRepository.save(UserTag.builder()
                        .user(user)
                        .tag(tags.get(tag-1))
                        .build());
            }
            user.setTags(newTags);
        }

        return UserProfileUpdateResponseDto.of(user);
    }

    private void createUserScrap(User user, Long targetId, User targetUser){
        userScrapRepository.save(UserScrap.builder()
                .user(user)
                .targetId(targetId)
                .build());
        targetUser.scrapCountUp();
    }

    @Transactional
    @Override
    public UserWorkEditResponseDTO updateWork(Long userId, UserWorkEditRequestDTO request) {
        val workId = request.workId();

        val work = workRepository.getWorkById(workId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_WORK.getMessage()));

        isOwner(work, userId);

        if (request.title() != null) {
            work.setTitle(request.title());
        }

        if (request.detail() != null) {
            work.setDetail(request.detail());
        }

        if (request.image() != null) {
            val deletePhotoUrl = work.getPhotoUrl();
            s3Service.deleteS3Image(deletePhotoUrl);

            val newPhotoUrl = request.image();
            work.setPhotoUrl(newPhotoUrl);
        }

        return UserWorkEditResponseDTO.of(workId);
    }

    private void chkClientAndDBStatus(boolean requestStatus, boolean DBStatus){
        if (requestStatus != DBStatus){
            throw new IllegalArgumentException(ExceptionMessage.NOT_MATCH_DB_SCRAP_STATUS.getMessage());
        }
    }

    private boolean isOwner(Work work, Long userId){
        if (!work.isOwner(userId)) {
            throw new WorkException(ExceptionMessage.NOT_WORK_OWNER.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return true;
    }
}
