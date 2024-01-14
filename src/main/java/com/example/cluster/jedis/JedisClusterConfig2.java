package com.example.cluster.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

//@Configuration
@Slf4j
public class JedisClusterConfig2 {

    public void test() {
        //java连接redis集群
        HashSet<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.61.223",7001));
        hostAndPorts.add(new HostAndPort("192.168.61.223",7002));
        hostAndPorts.add(new HostAndPort("192.168.61.223",7003));
        hostAndPorts.add(new HostAndPort("192.168.61.223",7004));
        hostAndPorts.add(new HostAndPort("192.168.61.223",7005));
        hostAndPorts.add(new HostAndPort("192.168.61.223",7006));
        //创建一个Jedis集群对象---需要传入redis集群服务的地址信息
        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        jedisCluster.set("k1","v1");
        System.out.println(jedisCluster.get("k1"));
    }
}
