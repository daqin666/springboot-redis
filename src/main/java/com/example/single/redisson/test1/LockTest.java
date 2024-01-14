package com.example.single.redisson.test1;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class LockTest {

    @Autowired
    private RedissonClient redisson;

    public String hello() {
        // 1.获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redisson.getLock("my-lock");
        lock.lock(); // 阻塞式等待。默认加的锁是30s时间
        try {
            // 1、锁的自动续期，运行期间自动给锁续上新的30s，无需担心业务时间长，锁过期会自动被释放
            // 2、加锁的业务只要运行完成，就不会给当前锁续期，即使不手动释放锁，锁默认在30s后自动释放，避免死锁
            System.out.println("加锁成功，执行业务代码..."+Thread.currentThread().getId());
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("释放锁..."+Thread.currentThread().getId());
            lock.unlock();
        }
        return "Hello!";
    }
}
