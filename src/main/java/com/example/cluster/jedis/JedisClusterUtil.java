package com.example.cluster.jedis;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.io.Serializable;

@Component
@Slf4j
public class JedisClusterUtil implements Serializable {

//    @Autowired
    private JedisCluster jedisCluster;

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void set(String key, String value) {
        jedisCluster.set(key, value);
        log.debug("JedisClusterUtil:set cache key={},value={}", key, value);
    }

    /**
     * 设置缓存对象
     *
     * @param key 缓存key
     * @param obj 缓存value
     */
    public <T> void setObject(String key, T obj, int expireTime) {
        jedisCluster.setex(key, expireTime, JSONObject.toJSONString(obj));
    }

    /**
     * 获取指定key的缓存
     *
     * @param key---JSON.parseObject(value, User.class);
     */
    public String getObject(String key) {
        return jedisCluster.get(key);
    }

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    public boolean hasKey(String key) {
        return jedisCluster.exists(key);
    }


    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public void setWithExpireTime(String key, String value, int expireTime) {
        jedisCluster.setex(key, expireTime, value);
        log.debug("JedisClusterUtil:setWithExpireTime cache key={},value={},expireTime={}", key, value, expireTime);
    }


    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    public String get(String key) {
        String value = jedisCluster.get(key);
        log.debug("JedisClusterUtil:get cache key={},value={}", key, value);
        return value;
    }

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    public void delete(String key) {
        jedisCluster.del(key);
        log.debug("JedisClusterUtil:delete cache key={}", key);
    }
}
