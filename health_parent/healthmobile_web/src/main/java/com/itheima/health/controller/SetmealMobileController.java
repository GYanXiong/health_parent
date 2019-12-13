package com.itheima.health.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/11/28 10:04
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/setmeal")
public class SetmealMobileController {

    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    // 查询所有套餐
    @RequestMapping(value = "/getSetmeal")
    public Result getSetmeal() {
        Jedis resource = jedisPool.getResource();
        try {
            List<String> hvals = resource.lrange(RedisConstant.SETMEAL_LIST_RESOURCE,0,-1);
            if (ObjectUtils.isEmpty(hvals)) {
                List<Setmeal> list = setmealService.findAll();
                System.out.println(list);
                for (Setmeal setmeal : list) {
                    String setMealString = JSONObject.toJSONString(setmeal);
                    resource.rpush(RedisConstant.SETMEAL_LIST_RESOURCE, setMealString);
                    hvals.add(setMealString);
                }
            }
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, hvals);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        } finally {
            resource.close();
        }

    }

    // 使用套餐id，查询套餐详情
    @RequestMapping(value = "/findById")
    public Result findById(Integer id) {
        Jedis resource = jedisPool.getResource();
        try {
            String hget = resource.hget(RedisConstant.SETMEAL_HASH_RESOURCE, id + "");
            if (StringUtils.isEmpty(hget)) {
                Setmeal setmeal = setmealService.findById(id);
                String data = JSONObject.toJSONString(setmeal);
                resource.hset(RedisConstant.SETMEAL_HASH_RESOURCE, setmeal.getId() + "", data);
                return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, data);
            } else {
                return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, hget);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        } finally {
            resource.close();
        }
    }
}
