package com.example.single.redisson.test1;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class RedissonConfig {
    /**
     * 所有对Redisson的使用都是通过RedissonClient对象
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        // 创建配置 指定redis地址及节点信息
        Config config = new Config();
//        config.useSingleServer().setAddress("XXX.XX.XX.X(redis地址):端口").setPassword("xxxxxxxxx");
        config.useSingleServer()
                .setAddress("redis://localhost:6379") // Redis服务器地址和端口
                .setPassword("your_password"); // 如果有密码，请设置密码

        // 根据config创建出RedissonClient实例
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
