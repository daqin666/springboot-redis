package com.example.cluster.lettuce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


public class LettuceClusterTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    void contextLoads() {
        String name = redisTemplate.opsForValue().get("name").toString();
        System.out.println(name);
    }
}
