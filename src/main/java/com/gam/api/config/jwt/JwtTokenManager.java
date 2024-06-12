package com.gam.api.config.jwt;

import com.gam.api.common.exception.AuthException;
import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.common.util.RedisUtil;
import com.gam.api.config.AuthConfig;
import com.gam.api.domain.user.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenManager {

    private final AuthConfig authConfig;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    private final UserDetailsServiceImpl userDetailsService;
    private final RedisTemplate<String, Object> redisTemplate;

    public String createAccessToken(Long userId) {
        val signatureAlgorithm = SignatureAlgorithm.HS256;
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey());
        val signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        val exp = ZonedDateTime.now(KST).toLocalDateTime().plusHours(3).atZone(KST).toInstant();

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        val signatureAlgorithm = SignatureAlgorithm.HS256;
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey());
        val signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        val exp = ZonedDateTime.now(KST).toLocalDateTime().plusDays(14).atZone(KST).toInstant();

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public boolean verifyAuthToken (String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }

    public String getUserIdFromAuthToken (String token) {
        try {
            val claims = getClaimsFromToken(token);

            return claims.getSubject();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new AuthException(ExceptionMessage.INVALID_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public AdminAuthentication getAuthentication(String token) {
        val userId = getUserIdFromAuthToken(token);

        if (RedisUtil.checkBlackListExist(redisTemplate, Long.parseLong(userId), token)) {
            throw new AuthException(ExceptionMessage.INVALID_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }

        val userDetails = userDetailsService.loadUserByUsername(userId);
        return new AdminAuthentication(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public long getAccessTokenExpirationRemainder(String accessToken) {
        val claims = getClaimsFromToken(accessToken);

        val expirationTimestamp = claims.getExpiration().toInstant();
        val currentTimestamp = ZonedDateTime.now(KST).toInstant();

        return Duration.between(currentTimestamp, expirationTimestamp).toMillis();
    }

    private Claims getClaimsFromToken (String token) throws SignatureException {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
