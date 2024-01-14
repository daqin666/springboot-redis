package com.example.single.redis.test1;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CacheTest {

    // key的定义：
    // T()里面写一个类型，表示当作一个类处理，后面调用该类方法
    //  如果是字符串，需要使用单引号
    // 如果要使用方法中的参数，需要在变量名前面加上#
//    @Cacheable(cacheNames = "myRedis", key = "T(com.qf.sbems.utils.MD5Utils).md5('EmployeeService_findById' + #id)")
//    public Employee findById(Integer id) {
//        System.out.println("进入方法，去数据库查询");
//        return employeeDAO.findById(id);
//    }

    /**
     * 存入或读取缓存
     * @param id
     * @param username
     * @return
     */
    @Cacheable(value = "spring.cache", key = "#id+#username")
    public String get(String id, String username) {
        //非空校验
        if(!StringUtils.hasLength(id) || !StringUtils.hasLength(username)) {
            return null;
        }
        System.out.println("执行了 get 方法");
        return "id: " + id + ", username: " + username;
    }

    /**
     * 更新缓存
     * 将当前方法的返回值更新到缓存中
     * @param id
     * @param username
     * @return
     */
    @CachePut(value = "spring.cache", key = "#id+#username")
    public String put(String id, String username)  {
        //非空校验
        if(!StringUtils.hasLength(id) || !StringUtils.hasLength(username)) {
            return null;
        }
        System.out.println("执行了 put 方法");
        return "id: " + id + ", username: " + username;
    }

    /**
     * 删除缓存
     * @param id
     * @param username
     */
    @CacheEvict(value = "spring.cache", key = "#id+#username")
    public void del(String id, String username) {
        System.out.println("执行了删除缓存");
    }
}
