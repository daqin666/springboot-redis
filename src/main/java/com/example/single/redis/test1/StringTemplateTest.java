package com.example.single.redis.test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StringTemplateTest {

    //引入 redis
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public String setRedis() {
        //存储 key-value 键值对: "username"-"jaychou"
        stringRedisTemplate.opsForValue().set("username", "jaychou");
        return "redis 存储成功！";
    }

    public String getRedis() {
        //通过 key 值读取 value
        String result = stringRedisTemplate.opsForValue().get("username");
        return result;
    }

}
