package com.gam.api.domain.social.kakao;

import com.gam.api.common.exception.AuthException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.domain.social.kakao.dto.KakaoUserResponse;
import com.gam.api.domain.social.dto.response.SocialLoginResponseDTO;
import com.gam.api.domain.social.service.SocialCommonService;
import com.gam.api.domain.social.service.SocialService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.gam.api.domain.social.dto.request.SocialLoginRequestDTO;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class KakaoSocialService implements SocialService {
    private final KakaoApiClient kakaoApiClient;
    private final SocialCommonService socialCommonService;

    @Override
    @Transactional
    public SocialLoginResponseDTO login(SocialLoginRequestDTO request) {
        KakaoUserResponse userResponse;
        try {
            userResponse = kakaoApiClient.getUserInformation("Bearer " + request.token());
        } catch (FeignException feignException) {
            throw new AuthException(ExceptionMessage.INVALID_KAKAO_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return socialCommonService.gamLogin(userResponse.id(), request.providerType(), request.deviceToken());
    }
}
