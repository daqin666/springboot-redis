package com.example.cluster.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@ConfigurationProperties("redis.server")
@Data
public class RedisConfig {

    private Integer model;
    private String keyPrefix;
    private RSingleton singleton;
    private RSentinel sentinel;
    private RCluster cluster;

    @Data
    static class RSingleton {
        private String hostName;
        private Integer port;
        private String password;
    }

    @Data
    static class RSentinel {
        private String[] nodes;
        private String master;
        private String password;
    }

    @Data
    static class RCluster {
        private String[] nodes;
        private Integer maxRedirects;
    }
}
