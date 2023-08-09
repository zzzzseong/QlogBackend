package com.Qlog.backend.service.storage;

import com.Qlog.backend.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RedisCacheService {

    private final RedisTemplate<String, User> userRedisTemplate;
    @Value("${jwt-expiration}") private Long expiration;

    @Transactional
    public void saveUser(String token, User user) {
        ValueOperations<String, User> valueOperations = userRedisTemplate.opsForValue();
        valueOperations.set("user:" + token, user);
        userRedisTemplate.expire(token, expiration, TimeUnit.MINUTES);
    }

    @Transactional
    public void removeUser(String token) {
        userRedisTemplate.delete("user:" + token);
    }

    public User findUser(String token) {
        ValueOperations<String, User> valueOperations = userRedisTemplate.opsForValue();
        return valueOperations.get("user:" + token);
    }
}
