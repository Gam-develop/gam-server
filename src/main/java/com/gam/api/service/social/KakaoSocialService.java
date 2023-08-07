package com.gam.api.service.social;

import com.gam.api.common.exception.AuthException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.common.util.RedisUtil;
import com.gam.api.config.AuthConfig;
import com.gam.api.config.GamConfig;
import com.gam.api.config.jwt.JwtTokenManager;
import com.gam.api.dto.kakao.KakaoAccessTokenResponse;
import com.gam.api.dto.social.response.SocialLoginResponseDTO;
import com.gam.api.entity.AuthProvider;
import com.gam.api.entity.Role;
import com.gam.api.entity.User;
import com.gam.api.entity.UserStatus;
import com.gam.api.repository.AuthProviderRepository;
import com.gam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.gam.api.external.kakao.KakaoApiClient;
import com.gam.api.external.kakao.KakaoAuthApiClient;
import com.gam.api.dto.social.request.SocialLoginRequestDTO;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KakaoSocialService implements SocialService {

    private final AuthProviderRepository authProviderRepository;
    private final UserRepository userRepository;

    private final KakaoAuthApiClient kakaoAuthApiClient;
    private final KakaoApiClient kakaoApiClient;

    private final JwtTokenManager jwtTokenManager;
    private final AuthConfig authConfig;
    private final GamConfig gamConfig;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public SocialLoginResponseDTO login(SocialLoginRequestDTO request) {
        KakaoAccessTokenResponse kakaoAccessTokenResponse;
        try {
            kakaoAccessTokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
                    "authorization_code",
                    authConfig.getKakaoClientSecret(),
                    authConfig.getKakaoRedirectUri(),
                    request.code()
            );
        } catch (RuntimeException e) {
            throw new AuthException(ExceptionMessage.KAKAO_CODE_ERROR.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val userResponse = kakaoApiClient.getUserInformation("Bearer " + kakaoAccessTokenResponse.getAccessToken());

        val authProvider = authProviderRepository.searchAuthProviderById(userResponse.id());

        if(Objects.nonNull(authProvider)) {
            val user = authProvider.getUser();
            val userId = user.getId();
            val accessToken = jwtTokenManager.createAccessToken(userId);
            val refreshToken = jwtTokenManager.createRefreshToken(userId);

            RedisUtil.saveRefreshToken(redisTemplate, refreshToken, userId);

            val isProfileCompleted = chkProfileCompleted(user);
            return SocialLoginResponseDTO.of(true, isProfileCompleted, userId, accessToken, gamConfig.getAppVersion());
        }

        val user = userRepository.save(User.builder()
                .role(Role.USER)
                .userStatus(UserStatus.NOT_PERMITTED)
                .build());

        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        RedisUtil.saveRefreshToken(redisTemplate, refreshToken, userId);

        authProviderRepository.save(AuthProvider.builder()
                        .id(userResponse.id())
                        .user(user)
                        .providerType(request.providerType())
                        .build());

        return SocialLoginResponseDTO.of(false, false, userId, accessToken, gamConfig.getAppVersion());
    }

    @Override
    public boolean chkProfileCompleted(User user) {
        return !Objects.isNull(user.getInfo()) && !Objects.isNull(user.getUserTag());
    }
}
