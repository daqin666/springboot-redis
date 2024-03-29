package com.example.cluster.lettuce;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterNodesCfg {

    private List<String> nodes;

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

}
