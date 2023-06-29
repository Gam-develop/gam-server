package com.gam.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gam.api.dto.user.UserScrapRequestDto;
import com.gam.api.dto.user.UserScrapResponseDto;
import com.gam.api.common.ExceptionMessage;
import com.gam.api.dto.user.UserInfoEditRequestDto;
import com.gam.api.dto.user.UserInfoEditResponseDto;
import com.gam.api.entity.User;
import com.gam.api.entity.UserScrap;
import com.gam.api.entity.UserTag;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.UserScrapRepository;
import com.gam.api.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.gam.api.common.message.ExceptionMessage.NOT_FOUND_USER;
import static com.gam.api.common.message.ExceptionMessage.NOT_MATCH_DB_SCRAP_STATUS;


@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;

    @Override
    public UserScrapResponseDto postUserScrap(Long userId, UserScrapRequestDto request) {
        User targetUser = userRepository.findById(request.targetUserId())
                .orElseThrow(()-> new EntityNotFoundException(NOT_FOUND_USER.getName()));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(NOT_FOUND_USER.getName()));

        Optional<UserScrap> userScrap = userScrapRepository.findByUserScrap_idAndTargetId(userId, targetUser.getId());
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
    private void createUserScrap(User user, Long targetId, User targetUser){
        userScrapRepository.save(
                UserScrap.builder()
                        .userScrap(user)
                        .targetId(targetId)
                        .build());
        targetUser.scrapCountUp();
    }
    private void chkClientAndDBStatus(boolean requestStatus, boolean DBStatus){
        if (requestStatus!=DBStatus){
            throw new IllegalArgumentException(NOT_MATCH_DB_SCRAP_STATUS.getName());
        }
    }

    @Override
    public UserInfoEditResponseDto editUserInfo(Long userId, UserInfoEditRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getName()));
        if (request.userInfo() != null) user.setInfo(request.userInfo());

        if (request.userDetail() != null) user.setDetail(request.userDetail());

        if (request.tags()!=null){
            List<Boolean> skeletonStatus = new ArrayList<>(Arrays.asList(false,false,false,false,false,false,false,false,false,false,false,false));
            List<String> skeletonTag = new ArrayList<>(Arrays.asList("ui_ux", "bi_bx", "industrial", "threeD", "graphic", "package", "motion", "Illustration", "edit", "fashion", "space", "character"));

            for(String tag: request.tags()){
                int index = skeletonTag.indexOf(tag);
                if (index==-1) throw new IllegalArgumentException(ExceptionMessage.BAD_REQUEST.getName());
                skeletonStatus.set(index,true);
            }

            UserTag userTag = user.getUserTag();
            userTag.setUserTag(skeletonStatus);
            userTagRepository.save(userTag);
            user.setUserTag(userTag);
        }

        userRepository.save(user);
        return UserInfoEditResponseDto.of(user.getInfo(),user.getDetail(),user.getUserTag().formattingUserTag());
    }

}