package com.example.sentinel.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

//@SpringBootTest
//@ActiveProfiles("JedisSentinel")
public class JedisSentinelTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    void contextLoads() {
        String name=redisTemplate.opsForValue().get("name").toString();
        System.out.println(name);
    }

}
