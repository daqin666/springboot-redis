package com.example.single.jedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@Slf4j
public class JedisTest {

    @Autowired
    JedisUtil jedisUtil;

    @ResponseBody
    public void getRedis(){
        jedisUtil.set("20182018","这是一条测试数据", 0);
        Long resExpire = jedisUtil.expire("20182018", 60, 0);//设置key过期时间
        log.info("resExpire="+resExpire);
        String res = jedisUtil.get("20182018", 0);
        System.out.println(res);
    }
}
