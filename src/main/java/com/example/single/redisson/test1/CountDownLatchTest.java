package com.example.single.redisson.test1;

import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class CountDownLatchTest {

    @Autowired
    private RedissonClient redisson;

    /**
     * 结果：我们先访问 /lockDoor 线程将会阻塞，连续访问五次 /go/1 ，输出 放假了。
     */

    public String lockDoor() throws InterruptedException {
        RCountDownLatch lockDoor = redisson.getCountDownLatch("lockDoor");
        lockDoor.trySetCount(5); // 设置计数为5
        lockDoor.await(); //等待闭锁完成
        return "放假啦...";
    }

    public String go(Integer id)  {
        RCountDownLatch lockDoor = redisson.getCountDownLatch("lockDoor");
        lockDoor.countDown(); // 计数减1
        return id+"班都走光了";
    }
}
