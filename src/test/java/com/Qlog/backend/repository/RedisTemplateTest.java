package com.Qlog.backend.repository;

import com.Qlog.backend.domain.Role;
import com.Qlog.backend.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTemplateTest {

    @Autowired private RedisTemplate<String, String> stringRedisTemplate;
    @Autowired private RedisTemplate<String, User> userRedisTemplate;

    @Test
    public void stringRedisTest() {
        //given
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String key = "stringKey";

        //when
        valueOperations.set(key, "hello");

        //then
        String value = valueOperations.get(key);
        System.out.println(value);
        Assertions.assertThat(value).isEqualTo("hello");
    }

    @Test
    public void JwtUserTestOpsForValue() {
        //given
        ValueOperations<String, User> valueOperations = userRedisTemplate.opsForValue();
        String key = "json_web_token";
        User user = new User("test", "test", "test", Role.USER);

        //when
        valueOperations.set(key, user);
        userRedisTemplate.expire(key, 5, TimeUnit.SECONDS);

        //then
//        User findUser = valueOperations.get(key);
//        System.out.println(findUser.getName());
        User findUser = valueOperations.get("json_web_token2");
        if(findUser == null) System.out.println("null");
        else System.out.println(findUser.getName());
    }

    /**
     * Redis Expire기능은 top-level key 에만 적용 가능하다.
     * HashKey에 Expire를 적용하고 싶다면, 수작업으로 구현해야 한다.
     * 따라서, Redis에 데이터를 나눠서 저장하고 싶을때, Redis Cluster를 구성하거나, MultiDB기능을 사용하면 되지만
     * Redis는 single-thread로 동작하기 때문에 MultiDB는 DB성능에 영향을 주기 때문에 redis에서 권장하지 않는다고 한다.
     * 따라서, Cluster or MultiDB를 사용해 구성하는 것보다 ${prefix}:${key} - ${value}와 같은 방식으로 구분하는것이 더 나은 방법일 것이다.
     * */
    @Test
    public void JwtUserTestOpsForHash() {
        //given
        HashOperations<String, Object, User> hashOperations = userRedisTemplate.opsForHash();
        Map<String, User> map = new HashMap<>();
        String key = "json_web_token";
        User user = new User("test", "test", "test", Role.USER);

        //when
        map.put(key, user);
        hashOperations.putAll("key", map);
        User u = (User) userRedisTemplate.opsForHash().get("key", key);

        //then
        System.out.println(u.getName());
    }
}