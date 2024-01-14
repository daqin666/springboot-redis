package com.example.single.redis.test2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

public class TemplateTest {

    @Autowired
//    @Qualifier("redisTemplate") // @Qualifier注解有助于消除歧义bean的自动注入。
    private RedisTemplate redisTemplate;

    public void RedisTest() {
        // redisTemplate  操作不同的数据类型
        // opsForValue 操作字符串
        // opsForList 操作List

        redisTemplate.opsForValue().set("author","1213");
        System.out.println(redisTemplate.opsForValue().get("author"));
    }
}
