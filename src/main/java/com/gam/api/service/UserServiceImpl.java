package com.gam.api.service;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.UserExternalLinkResponseDto;
import com.gam.api.dto.user.response.UserProfileUpdateResponseDto;
import com.gam.api.dto.user.response.UserScrapResponseDto;
import com.gam.api.entity.User;
import com.gam.api.entity.UserScrap;
import com.gam.api.entity.UserTag;
import com.gam.api.repository.TagRepository;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.UserScrapRepository;
import com.gam.api.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gam.api.common.message.ExceptionMessage.NOT_FOUND_USER;
import static com.gam.api.common.message.ExceptionMessage.NOT_MATCH_DB_SCRAP_STATUS;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final TagRepository tagRepository;

    @Transactional
    @Override
    public UserScrapResponseDto scrapUser(Long userId, UserScrapRequestDto request) {
        val targetUser = userRepository.findById(request.targetUserId())
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER.getMessage()));
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER.getMessage()));

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
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER.getMessage()));
        user.setAdditionalLink(request.externalLink());
        return UserExternalLinkResponseDto.of(user.getAdditionalLink());
    }

    @Transactional
    @Override
    public UserProfileUpdateResponseDto updateMyProfile(Long userId, UserProfileUpdateRequestDto request){
        val user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_USER.getMessage()));
        val userTags = userTagRepository.findAllByUser_Id(userId);
        for (UserTag tag: userTags) {
            System.out.println(tag.getTag().getId());
            System.out.println(tag.getTag().getTagName());
        }
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
                user.addUserTag(
                        UserTag.builder()
                        .user(user)
                        .tag(tags.get(tag-1))
                        .build());
                System.out.println(tags.get(tag-1));
            }
//            user.setTags(newTags);
            user.getUserTag();
        }
        return null;
    }

    private void createUserScrap(User user, Long targetId, User targetUser){
        userScrapRepository.save(UserScrap.builder()
                            .user(user)
                            .targetId(targetId)
                            .build());
        targetUser.scrapCountUp();
    }
    private void chkClientAndDBStatus(boolean requestStatus, boolean DBStatus){
        if (requestStatus!=DBStatus){
            throw new IllegalArgumentException(NOT_MATCH_DB_SCRAP_STATUS.getMessage());
        }
    }
}
