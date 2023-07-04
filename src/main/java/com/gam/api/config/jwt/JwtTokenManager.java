package com.gam.api.config.jwt;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.config.AuthConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenManager {

    private final AuthConfig authConfig;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    public String createAccessToken(Long userId) {
        val signatureAlgorithm= SignatureAlgorithm.HS256;
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey());
        val signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        val exp = new Date().toInstant().atZone(KST)
                .toLocalDateTime().plusHours(6).atZone(KST).toInstant();

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
        val exp = new Date().toInstant().atZone(KST)
                .toLocalDateTime().plusDays(7).atZone(KST).toInstant();

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public boolean verifyAuthToken (String token) {
        try {
            val claims = getClaimsFromToken(token);

            val now = LocalDateTime.now(KST);
            val exp = claims.getExpiration().toInstant().atZone(KST).toLocalDateTime();
            if (exp.isBefore(now)) return false;

            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }

    public Long getUserIdFromAuthToken (String token) {
        try {
            val claims = getClaimsFromToken(token);

            val now = LocalDateTime.now(KST);
            val exp = claims.getExpiration().toInstant().atZone(KST).toLocalDateTime();
            if (exp.isBefore(now)) throw new RuntimeException(ExceptionMessage.EXPIRED_TOKEN.getName());

            return Long.parseLong(claims.getSubject());
        } catch (SignatureException e) {
            throw new RuntimeException(ExceptionMessage.INVALID_SIGNATURE.getName());
        }
    }

    public AdminAuthentication getAuthentication(String token) {
        return new AdminAuthentication(getUserIdFromAuthToken(token), null, null);
    }

    private Claims getClaimsFromToken (String token) throws SignatureException {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
