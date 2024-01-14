package com.example.single.serializer;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

public class JdkSerializer {

    /**
     * redisTemplate 序列化使用的jdkSerializeable, 存储二进制字节码, 所以自定义序列化类
     * redis默认就这个二进制数据，不便观察
     * @param redisConnectionFactory
     * @return
     */
//    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
//        GenericFastJsonRedisSerializer redisSerializer = new GenericFastJsonRedisSerializer();
        JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
