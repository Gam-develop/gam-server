package com.gam.api.domain.social.service;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.common.exception.AuthException;
import com.gam.api.common.util.RedisUtil;
import com.gam.api.config.GamConfig;
import com.gam.api.config.jwt.JwtTokenManager;
import com.gam.api.domain.user.entity.AuthProvider;
import com.gam.api.domain.user.entity.ProviderType;
import com.gam.api.domain.user.entity.Role;
import com.gam.api.domain.user.entity.UserStatus;
import com.gam.api.domain.social.dto.request.SocialLogoutRequestDTO;
import com.gam.api.domain.social.dto.request.SocialRefreshRequestDTO;
import com.gam.api.domain.social.dto.response.SocialLoginResponseDTO;
import com.gam.api.domain.social.dto.response.SocialRefreshResponseDTO;
import com.gam.api.domain.user.repository.AuthProviderRepository;
import com.gam.api.domain.user.entity.User;
import com.gam.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SocialCommonServiceImpl implements SocialCommonService {
    private final JwtTokenManager jwtTokenManager;
    private final AuthProviderRepository authProviderRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final GamConfig gamConfig;

    private boolean chkProfileCompleted(User user) {
        return !Objects.isNull(user.getInfo()) && !Objects.isNull(user.getUserTag());
    }

    private void saveDeviceToken(User user, String deviceToken) {
        user.updateDeviceToken(deviceToken);
    }

    @Transactional
    private SocialLoginResponseDTO reLogin(User user, String deviceToken) {
        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        RedisUtil.saveRefreshToken(redisTemplate, refreshToken, userId);

        saveDeviceToken(user, deviceToken);

        if (user.getUserStatus() == UserStatus.WITHDRAWAL) {
            user.setUserStatus(UserStatus.PERMITTED);
        }

        val isProfileCompleted = chkProfileCompleted(user);
        return SocialLoginResponseDTO.of(isProfileCompleted, userId, accessToken, refreshToken, gamConfig.getAppVersion());
    }

    private SocialLoginResponseDTO SignUpAndLogin(
            String thirdPartyUserId,
            ProviderType providerType,
            String deviceToken
    ) {
        val user = userRepository.save(User.builder()
                .role(Role.USER)
                .userStatus(UserStatus.NOT_PERMITTED)
                .build());

        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        RedisUtil.saveRefreshToken(redisTemplate, refreshToken, userId);

        authProviderRepository.save(AuthProvider.builder()
                .id(thirdPartyUserId)
                .user(user)
                .providerType(providerType)
                .build());

        saveDeviceToken(user, deviceToken);

        return SocialLoginResponseDTO.of( false, userId, accessToken, refreshToken, gamConfig.getAppVersion());
    }

    @Override
    @Transactional
    public void logout(SocialLogoutRequestDTO socialLogoutRequestDTO) {
        val accessRemainTime = jwtTokenManager.getAccessTokenExpirationRemainder(socialLogoutRequestDTO.accessToken());

        val tokenUserId = jwtTokenManager.getUserIdFromAuthToken(socialLogoutRequestDTO.accessToken());

        val userId = Long.parseLong(tokenUserId);

        if (RedisUtil.checkBlackListExist(redisTemplate, userId, socialLogoutRequestDTO.accessToken())) {
            throw new AuthException(ExceptionMessage.ALREADY_LOGOUT_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (accessRemainTime > 0) {
            RedisUtil.saveBlackList(redisTemplate, socialLogoutRequestDTO.accessToken(), userId, accessRemainTime);
        }

        val redisRefreshKey = "refresh:user:" + userId;

        val savedRefreshToken = RedisUtil.getRefreshToken(redisTemplate, userId);

        if (Objects.nonNull(savedRefreshToken) && Objects.equals(socialLogoutRequestDTO.refreshToken(), savedRefreshToken.toString())) {
            RedisUtil.deleteKey(redisTemplate, redisRefreshKey);
        }
    }

    @Override
    @Transactional
    public SocialRefreshResponseDTO refresh(SocialRefreshRequestDTO socialRefreshRequestDTO) {
        val tokenUserId = jwtTokenManager.getUserIdFromAuthToken(socialRefreshRequestDTO.refreshToken());

        val userId = Long.parseLong(tokenUserId);

        val user = userRepository.getUserById(userId)
                .orElseThrow(EntityNotFoundException::new);

        val isProfileCompleted = chkProfileCompleted(user);

        if (jwtTokenManager.verifyAuthToken(socialRefreshRequestDTO.accessToken())) {
            return SocialRefreshResponseDTO.of(isProfileCompleted, userId, socialRefreshRequestDTO.accessToken(), socialRefreshRequestDTO.refreshToken(), gamConfig.getAppVersion());
        }

        if (!jwtTokenManager.verifyAuthToken(socialRefreshRequestDTO.refreshToken())) {
           throw new AuthException(ExceptionMessage.EXPIRED_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val redisRefreshToken = RedisUtil.getRefreshToken(redisTemplate, userId);

        if (Objects.isNull(redisRefreshToken)) {
            throw new AuthException(ExceptionMessage.INVALID_REFRESH_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        if (!redisRefreshToken.toString().equals(socialRefreshRequestDTO.refreshToken())) {
            val spoiledKey = "refresh:user:" + userId;
            RedisUtil.deleteKey(redisTemplate, spoiledKey);
            throw new AuthException(ExceptionMessage.INVALID_REFRESH_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        RedisUtil.saveRefreshToken(redisTemplate, refreshToken, userId);

        return SocialRefreshResponseDTO.of(isProfileCompleted, userId, accessToken, refreshToken, gamConfig.getAppVersion());
    }

    @Override
    @Transactional
    public SocialLoginResponseDTO gamLogin(String thirdPartyUserId, ProviderType providerType, String deviceToken) {
        val authProvider = authProviderRepository.searchAuthProviderById(thirdPartyUserId);

        if (Objects.nonNull(authProvider)) {
            return reLogin(authProvider.getUser(), deviceToken);
        }

        return SignUpAndLogin(thirdPartyUserId, providerType, deviceToken);
    }
}
