package com.example.cluster.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class JedisClusterUtilTest {

    @Autowired
    JedisClusterUtil jedisClusterUtil;

    public void set() {
        jedisClusterUtil.set("name", "jedis");
    }

    public void get() {
        String name = jedisClusterUtil.get("name");
        System.out.println(name);
    }

    public void setObject() {
        Map<String, Object> man = new HashMap<>();
        man.put("id", "10087L");
        man.put("name", "zhangSan");
        jedisClusterUtil.setObject("10087L", man, 200);
    }

    public void getObject() {
        System.out.println(jedisClusterUtil.getObject("10087L"));
    }

    public void hasKey() {
    }

    public void setWithExpireTime() {
    }

    public void delete() {
        jedisClusterUtil.delete("10087L");
    }
}

