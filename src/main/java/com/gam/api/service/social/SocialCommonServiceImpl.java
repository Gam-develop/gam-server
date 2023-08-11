package com.gam.api.service.social;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.common.exception.AuthException;
import com.gam.api.common.util.RedisUtil;
import com.gam.api.config.jwt.JwtTokenManager;
import com.gam.api.dto.social.request.SocialLogoutRequestDTO;
import com.gam.api.dto.social.request.SocialRefreshRequestDTO;
import com.gam.api.dto.social.response.SocialRefreshResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SocialCommonServiceImpl implements SocialCommonService {
    private final JwtTokenManager jwtTokenManager;
    private final RedisTemplate<String, Object> redisTemplate;

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
        val tokenUserId = jwtTokenManager.getUserIdFromAuthToken(socialRefreshRequestDTO.accessToken());

        val userId = Long.parseLong(tokenUserId);

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

        return SocialRefreshResponseDTO.of(accessToken, refreshToken);
    }
}
