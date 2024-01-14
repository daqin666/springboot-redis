package com.example.lock;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LockUtil {

    private RedisTemplate redisTemplate;

    /**
     * 青铜级
     * 问题：setnx 占锁成功，业务代码出现异常或者服务器宕机，没有执行删除锁的逻辑，就造成了死锁
     * @throws InterruptedException --
     */
    public void test1() throws InterruptedException {
        // 1.先抢占锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "123");
        if(lock) {
            // 2.抢占成功，执行业务

            // 3.解锁
            redisTemplate.delete("lock");

        } else {
            // 4.休眠一段时间
            Thread.sleep(100);
            // 5.抢占失败，等待锁释放

        }
    }

    /**
     * 白银级
     * 问题：因为占锁和设置过期时间是分两步执行的，所以如果在这两步之间发生了异常，则锁的过期时间根本就没有设置成功。
     * 所以和青铜方案有一样的问题：锁永远不能过期。
     */
    public void test2() {
        // 1.先抢占锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "123");
        if(lock) {
            // 2.在 10s 以后，自动清理 lock
            redisTemplate.expire("lock", 10, TimeUnit.SECONDS);
            // 3.抢占成功，执行业务

            // 4.解锁
            redisTemplate.delete("lock");

        }
    }

    /**
     * 黄金级
     * 问题：用户 A 处理任务所需要的时间大于锁自动清理（开锁）的时间，所以在自动开锁后，又有其他用户抢占到了锁。
     * 当用户 A 完成任务后，会把其他用户抢占到的锁给主动打开
     */
    public void test3() {
        // 1.先抢占锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "123", 10, TimeUnit.SECONDS);
        if(lock) {
            // 2.抢占成功，执行业务

            // 3.解锁
            redisTemplate.delete("lock");

        }
    }

    /**
     * 白金级
     * 问题：第 4 步和第 5 步并不是原子性的。
     */
    public void test4() throws InterruptedException {
        // 1.生成唯一 id
        String uuid = UUID.randomUUID().toString();
        // 2. 抢占锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
        if(lock) {
            System.out.println("抢占成功：" + uuid);
            // 3.抢占成功，执行业务

            // 4.获取当前锁的值
            String lockValue = (String) redisTemplate.opsForValue().get("lock");
            // 5.如果锁的值和设置的值相等，则清理自己的锁
            if(uuid.equals(lockValue)) {
                System.out.println("清理锁：" + lockValue);
                redisTemplate.delete("lock");
            }

        } else {
            System.out.println("抢占失败，等待锁释放");
            // 4.2.休眠一段时间
            Thread.sleep(100);
            // 5.2.抢占失败，等待锁释放

        }
    }

    /**
     * 钻石级
     */
    public void test5() throws InterruptedException {
        // 1.生成唯一 id
        String uuid = UUID.randomUUID().toString();
        // 2. 抢占锁
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 10, TimeUnit.SECONDS);
        if(lock) {
            System.out.println("抢占成功：" + uuid);
            // 3.抢占成功，执行业务

            // 4.脚本解锁
            String script = "if redis.call('get',KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del',KEYS[1]) else return 0 end";
            redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class),
                    Arrays.asList("lock"), uuid);

        } else {
            System.out.println("抢占失败，等待锁释放");
            // 4.2.休眠一段时间
            Thread.sleep(100);
            // 5.2.抢占失败，等待锁释放
        }
    }
}
