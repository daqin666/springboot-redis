package com.example.single.redisson.test1;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ReadWriteTest {

    @Autowired
    RedissonClient redisson;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 当我们访问 write写入数据时，因为线程睡眠了30s(模拟业务)，此时我们访问
     * read 将会一直阻塞，等待写锁释放，读锁才能占锁从而获取执行业务。
     */

    public String writeValue(){
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.writeLock();
        String s = "";
        try {
            s = UUID.randomUUID().toString();
            // 模拟业务时间
            Thread.sleep(30000);
        } catch (Exception e){

        }finally {
            rLock.unlock();
        }
        redisTemplate.opsForValue().set("writeValue",s);
        return s;
    }

    public String readValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        //加读锁
        RLock rLock = readWriteLock.readLock();
        try {
            rLock.lock();
            s = (String) redisTemplate.opsForValue().get("writeValue");

            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
        return s;
    }
}
