package com.example.single.redisson.test1;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class RedissonTest {

    @Autowired
    private RedissonClient redisson;

    /**
     * 示例方法：向 Redis 写入数据
     *
     * @param key   Redis 键
     * @param value Redis 值
     */
    public void writeToRedis(String key, String value) {
        // 使用 RedissonClient 执行写入操作
        redisson.getBucket(key).set(value);
    }

    /**
     * 示例方法：从 Redis 读取数据
     *
     * @param key Redis 键
     * @return Redis 值
     */
    public String readFromRedis(String key) {
        // 使用 RedissonClient 执行读取操作
        return (String) redisson.getBucket(key).get();
    }
}
