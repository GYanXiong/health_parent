package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Category;
import com.itheima.health.service.CategoryService;
import com.itheima.health.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/category")
public class CategoryMobileController {


    @Reference
    private CategoryService categoryService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/findById")
    public Category findById(Integer id) {
        return categoryService.findById(id);
    }

    @RequestMapping("/findAll")
    public String findByCondition() {
        Jedis jedis = jedisPool.getResource();
        String listJson = jedis.get(RedisConstant.CATEGORY_LIST_RESOURCE);

        if (!StringUtils.isEmpty(listJson)) {
            return listJson;
        }
        List<Category> categorys = categoryService.findAll();
        listJson = JSONObject.toJSONString(categorys);
        jedis.set(RedisConstant.CATEGORY_LIST_RESOURCE, listJson);
        jedis.close();
        return listJson;
    }

}
