package com.gam.api.service.user;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.dto.user.request.UserExternalLinkRequestDto;
import com.gam.api.dto.user.request.UserOnboardRequestDTO;
import com.gam.api.dto.user.request.UserProfileUpdateRequestDto;
import com.gam.api.dto.user.request.UserScrapRequestDto;
import com.gam.api.dto.user.response.*;
import com.gam.api.entity.User;
import com.gam.api.entity.UserScrap;
import com.gam.api.entity.UserTag;
import com.gam.api.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;

    @Transactional
    @Override
    public UserScrapResponseDTO scrapUser(Long userId, UserScrapRequestDto request) {
        val targetUser = findUser(request.targetUserId());
        val user = findUser(userId);

        val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, targetUser.getId());

        if (userScrap.isPresent()) {
            chkClientAndDBStatus(userScrap.get().isStatus(), request.currentScrapStatus());

            if (userScrap.get().isStatus()) targetUser.scrapCountDown();
            else targetUser.scrapCountUp();

            userScrap.get().setScrapStatus(!userScrap.get().isStatus());

            return UserScrapResponseDTO.of(targetUser.getId(), targetUser.getUserName(), userScrap.get().isStatus());
        }
        createUserScrap(user, targetUser.getId(), targetUser);

        return UserScrapResponseDTO.of(targetUser.getId(), targetUser.getUserName(), true);
    }

    @Transactional
    @Override
    public UserExternalLinkResponseDTO updateExternalLink(Long userId, UserExternalLinkRequestDto request){
        val user = findUser(userId);
        user.setAdditionalLink(request.externalLink());
        return UserExternalLinkResponseDTO.of(user.getAdditionalLink());
    }

    @Override
    public UserMyProfileResponseDTO getMyProfile(Long userId){
        val user = findUser(userId);
        return UserMyProfileResponseDTO.of(user);
    }

    @Transactional
    @Override
    public UserProfileUpdateResponseDTO updateMyProfile(Long userId, UserProfileUpdateRequestDto request){
        val user = findUser(userId);

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
            createUserTags(newTags, user);
        }

        return UserProfileUpdateResponseDTO.of(user);
    }

    @Transactional
    @Override
    public void onboardUser(Long userId, UserOnboardRequestDTO userOnboardRequestDTO) {
        val userName = userOnboardRequestDTO.userName();
        val info = userOnboardRequestDTO.info();
        val tags = userOnboardRequestDTO.tags();

        if (tags.length > 3) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_TAG_COUNT.getMessage());
        }

        val user = findUser(userId);

        createUserTags(tags, user);
        user.onboardUser(userName, info, tags);
    }

    @Override
    public UserNameCheckResponseDTO checkUserNameDuplicated(String userName) {
        val isDuplicated = userRepository.existsByUserName(userName);
        return UserNameCheckResponseDTO.of(isDuplicated);
    }

    @Override
    public List<UserScrapsResponseDTO> getUserScraps(Long userId) {
        val scraps = userScrapRepository.getAllByUser_idAndStatus(userId, true);

        val scrapUsers = scraps.stream()
                .map((scrap) -> {
                    val scrapId = scrap.getId();
                    val targetId = scrap.getTargetId();
                    val targetUser =  userRepository.findById(targetId)
                            .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
                    return UserScrapsResponseDTO.of(scrapId, targetUser);
                    })
                .collect(Collectors.toList());
            return scrapUsers;
    }

    @Override
    public UserProfileResponseDTO getUserProfile(Long myId, Long userId) {
        val user = findUser(userId);
        val userScrap = userScrapRepository.findByUser_idAndTargetId(myId, userId);

        if(userScrap.isPresent()){
            return UserProfileResponseDTO.of(true, user);
        }
        return UserProfileResponseDTO.of(false, user);
    }

    @Override
    public List<UserResponseDTO> getPopularDesigners(Long userId) {
        val users = userRepository.findAllByIdNotOrderByScrapCountDesc(userId);

        return users.stream().map((user) -> {
            val userScrap = userScrapRepository.findByUser_idAndTargetId(userId, user.getId());
            if (userScrap.isPresent()){
                return UserResponseDTO.of(user,true);
            }
            return UserResponseDTO.of(user, false);
         }).collect(Collectors.toList());
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));
    }

    private void createUserScrap(User user, Long targetId, User targetUser){
        userScrapRepository.save(UserScrap.builder()
                .user(user)
                .targetId(targetId)
                .build());
        targetUser.scrapCountUp();
    }


    private void chkClientAndDBStatus(boolean requestStatus, boolean DBStatus){
        if (requestStatus != DBStatus){
            throw new IllegalArgumentException(ExceptionMessage.NOT_MATCH_DB_SCRAP_STATUS.getMessage());
        }
    }

    private void createUserTags(int[] newTags, User user) {
        val tags = tagRepository.findAll();

        userTagRepository.deleteAllByUser_id(user.getId());

        for (Integer tag: newTags) {
            userTagRepository.save(UserTag.builder()
                    .user(user)
                    .tag(tags.get(tag-1))
                    .build());
        }
        user.setTags(newTags);
    }
}
