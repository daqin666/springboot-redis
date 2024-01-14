package com.example.sentinel.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.JedisPool;

//@SpringBootTest
//@ActiveProfiles("lettuceSentinel")
class LettuceSentinelTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


//    @Test
    void contextLoads() {
        String name = redisTemplate.opsForValue().get("name").toString();
        System.out.println(name);
    }

}
