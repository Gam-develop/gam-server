package com.gam.api.service;

import com.gam.api.config.AuthConfig;
import com.gam.api.config.jwt.JwtTokenManager;
import com.gam.api.dto.social.SocialLoginResponseDTO;
import com.gam.api.entity.AuthProvider;
import com.gam.api.entity.Role;
import com.gam.api.entity.User;
import com.gam.api.repository.AuthProviderRepository;
import com.gam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import com.gam.api.external.kakao.KakaoApiClient;
import com.gam.api.external.kakao.KakaoAuthApiClient;
import com.gam.api.dto.social.SocialLoginRequestDTO;

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

    @Override
    public SocialLoginResponseDTO login(SocialLoginRequestDTO request) {
        val kakaoAccessTokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
                "authorization_code",
                authConfig.getKakaoClientSecret(),
                authConfig.getKakaoRedirectUri(),
                request.code()
        );

        val userResponse = kakaoApiClient.getUserInformation("Bearer " + kakaoAccessTokenResponse.getAccessToken());

        val authProvider = authProviderRepository.searchAuthProviderById(userResponse.id());

        if(Objects.nonNull(authProvider)) {
            val userId = authProvider.getUser().getId();
            val accessToken = jwtTokenManager.createAccessToken(userId);
            return SocialLoginResponseDTO.of(true, userId, accessToken);
        }

        val user = userRepository.save(User.builder()
                .role(Role.USER)
                .build());

        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);

        authProviderRepository.save(AuthProvider.builder()
                        .id(userResponse.id())
                        .user(user)
                        .providerType(request.providerType())
                        .build());

        return SocialLoginResponseDTO.of(false, userId, accessToken);
    }

    @Override
    public void logout(Long userId) {

    }
}
