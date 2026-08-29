package com.socialapp.userservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    private static final long REFRESH_EXPIRATION_DAYS = 7;

    // Generate and store refresh token
    public String createRefreshToken(Long userId, String username) {
        String token = UUID.randomUUID().toString();

        // Store: key = "refresh:<token>", value = "userId:username"
        redisTemplate.opsForValue().set(
                "refresh:" + token,
                userId + ":" + username,
                REFRESH_EXPIRATION_DAYS, TimeUnit.DAYS
        );

        return token;
    }

    // Validate and get user info from refresh token
    public String validate(String token) {
        return redisTemplate.opsForValue().get("refresh:" + token);
    }

    // Delete refresh token (logout or rotation)
    public void delete(String token) {
        redisTemplate.delete("refresh:" + token);
    }
}