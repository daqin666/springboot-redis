package com.example.lua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

//@Service
public class RedisLuaTest {

//    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Long executeLuaScript(String key, long incrementBy) {
        ClassPathResource luaScriptResource = new ClassPathResource("script/incrementBy.lua");
        String luaScript;

        try {
            luaScript = new String(Files.readAllBytes(luaScriptResource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Lua script file", e);
        }

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(key), incrementBy);
    }


}
