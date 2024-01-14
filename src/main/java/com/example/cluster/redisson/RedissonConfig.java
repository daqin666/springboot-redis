package com.example.cluster.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
//@Component
public class RedissonConfig {

//    @Autowired
    private RedisConfig redisConfig;

    private static final String REDIS_PROTO = "redis://";

//    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        if (redisConfig.getModel() == 2) {
            // sentinel模式
            String[] nodes = redisConfig.getSentinel().getNodes();
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = REDIS_PROTO + nodes[i];
            }
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisConfig.getSentinel().getMaster());
            sentinelServersConfig.addSentinelAddress(nodes);

            if (!StringUtils.isEmpty(redisConfig.getSentinel().getPassword())) {
                sentinelServersConfig.setPassword(redisConfig.getSentinel().getPassword());
            }

            return Redisson.create(config);
        } else if (redisConfig.getModel() == 3) {
            // 集群模式
            String[] nodes = redisConfig.getCluster().getNodes();
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = REDIS_PROTO + nodes[i];
            }
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            clusterServersConfig.addNodeAddress(nodes);
            return Redisson.create(config);
        } else {
            // 单机模式
            String address =
                    REDIS_PROTO + redisConfig.getSingleton().getHostName() + ":" + redisConfig.getSingleton()
                            .getPort();
            SingleServerConfig serverConfig = config.useSingleServer();
            serverConfig.setAddress(address);
            if (!StringUtils.isEmpty(redisConfig.getSingleton().getPassword())) {
                serverConfig.setPassword(redisConfig.getSingleton().getPassword());
            }
            return Redisson.create(config);
        }
    }
}
