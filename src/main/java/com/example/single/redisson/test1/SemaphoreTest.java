package com.example.single.redisson.test1;

import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    @Autowired
    private RedissonClient redisson;

    public String park() {
        RSemaphore park = redisson.getSemaphore("park");
        try {
            // 获取一个信号量（redis中信号量值-1）,如果redis中信号量为0了，则在这里阻塞住，
            // 直到信号量大于0，可以拿到信号量，才会继续执行。
            park.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "ok";
    }

    public String go() {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();  //释放一个信号量（redis中信号量值+1）
        return "ok";
    }

    public void test() {
        RSemaphore rSemaphore = redisson.getSemaphore("semaphore");
        // 设置5个许可，模拟五个停车位
        rSemaphore.trySetPermits(5);
        // 创建10个线程，模拟10辆车过来停车
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    rSemaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "进入停车场...");
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
                    System.out.println(Thread.currentThread().getName() + "离开停车场...");
                    rSemaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }, "A" + i).start();
        }
        try {
            TimeUnit.MINUTES.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
