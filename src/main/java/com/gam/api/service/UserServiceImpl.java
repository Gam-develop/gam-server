package com.gam.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gam.api.dto.user.UserScrapRequestDto;
import com.gam.api.dto.user.UserScrapResponseDto;
import com.gam.api.entity.User;
import com.gam.api.entity.UserScrap;
import com.gam.api.repository.UserRepository;
import com.gam.api.repository.UserScrapRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.gam.api.common.message.ExceptionMessage.NOT_FOUND_USER;
import static com.gam.api.common.message.ExceptionMessage.NOT_MATCH_DB_SCRAP_STATUS;


@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserScrapRepository userScrapRepository;
    private final UserRepository userRepository;

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


}
