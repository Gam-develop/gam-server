package com.gam.api.common.util;

import lombok.val;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RedisUtil {
    public static void saveBlackList(RedisTemplate<String, Object> redisTemplate, String blackListToken, Long userId, Long accessRemainTime) {
        val redisKey = "blacklist:user:" + userId + ":" + blackListToken;

        redisTemplate.opsForValue().set(redisKey, "true", accessRemainTime, TimeUnit.SECONDS);
    }

    public static void saveRefreshToken(RedisTemplate<String, Object> redisTemplate, String refreshToken, Long userId) {
        val redisKey = "refresh:user:" + userId;

        redisTemplate.opsForValue().set(redisKey, refreshToken, 14, TimeUnit.DAYS);
    }

    public static boolean checkBlackListExist(RedisTemplate<String, Object> redisTemplate, Long userId, String accessToken) {
        val redisKey = "blacklist:user:" + userId + ":" + accessToken;
        return !Objects.isNull(redisTemplate.opsForValue().get(redisKey));
    }

    public static Object getRefreshToken(RedisTemplate<String, Object> redisTemplate, Long userId) {
        val redisKey = "refresh:user:" + userId;
        return redisTemplate.opsForValue().get(redisKey);
    }

    public static void deleteKey(RedisTemplate<String, Object> redisTemplate, String key) {
        redisTemplate.delete(key);
    }
}
