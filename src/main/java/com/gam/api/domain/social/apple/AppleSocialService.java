package com.gam.api.domain.social.apple;

import com.gam.api.common.exception.AuthException;
import com.gam.api.domain.social.dto.response.SocialLoginResponseDTO;
import com.gam.api.domain.social.service.SocialCommonService;
import com.gam.api.domain.social.service.SocialService;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.gam.api.domain.social.dto.request.SocialLoginRequestDTO;

import javax.transaction.Transactional;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AppleSocialService implements SocialService {

    private final SocialCommonService socialCommonService;

    @Override
    @Transactional
    public SocialLoginResponseDTO login(SocialLoginRequestDTO request) {
        val appleUserId = getUserInfo(request.token());
        return socialCommonService.gamLogin(appleUserId, request.providerType(), request.deviceToken());
    }

    private String getUserInfo (String idToken) {
        try {
            val signedJWT = SignedJWT.parse(idToken);
            val payload = signedJWT.getJWTClaimsSet();
            val userId = payload.getSubject();
            return userId;
        } catch (ParseException e) {
            throw new AuthException("잘못된 토큰입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
