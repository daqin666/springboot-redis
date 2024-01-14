package com.example.cluster.lettuce;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Profile("lettuceCluster")
public class LettuceClusterConfig {

//    @Autowired
    private RedisClusterNodesCfg redisClusterNodesCfg;


    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration rcc=new RedisClusterConfiguration();
        List<String> nodesList=redisClusterNodesCfg.getNodes();
        String host=null;
        int port=0;
        for(String node:nodesList) {
            host=node.split(":")[0];
            port=Integer.valueOf(node.split(":")[1]);
            rcc.addClusterNode(new RedisNode(host,port));
        }
        return new LettuceConnectionFactory(rcc);
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
