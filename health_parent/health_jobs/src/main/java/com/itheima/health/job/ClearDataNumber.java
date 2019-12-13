package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.service.OrderService;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.Date;

public class ClearDataNumber {

    @Reference
    private OrderSettingService orderSettingService;
@Autowired
    private JedisPool jedisPool;

    //删除历史预约设置
    public void deleteNumberByOrderDate() {

        System.out.println(new Date() + "执行历史数据预约设置清除");
        orderSettingService.deleteNumberByOrderDate();
        System.out.println(orderSettingService);
    }
//删除历史Redis的Key
    @Reference
    public void deleteRedisKey() {
        System.out.println("删除RedisKey");
        Jedis resource = jedisPool.getResource();
        resource.del(RedisConstant.ORDER_HASH_RESOURCE);
        resource.close();
    }

}
