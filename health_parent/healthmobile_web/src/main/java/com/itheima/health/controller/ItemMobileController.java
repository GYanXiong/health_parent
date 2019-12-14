package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Item;
import com.itheima.health.service.ItemService;
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

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/item")
public class ItemMobileController {

    @Reference
    private ItemService itemService;
    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/findById")
    public String findById(Integer id) throws UnsupportedEncodingException {
        Jedis jedis = jedisPool.getResource();
        String itemJson = jedis.hget(RedisConstant.ITEM_LIST_RESOURCE, id + "");
        if (!StringUtils.isEmpty(itemJson)) {
            return itemJson;
        }
        Item item = itemService.findById(id);
        if (item != null) {
            itemJson = JSONObject.toJSONString(item);
            jedis.hset(RedisConstant.ITEM_LIST_RESOURCE, id + "", itemJson);
            jedis.close();
        }
        return itemJson;
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody QueryPageBean queryPageBean) {
        if (queryPageBean.getPageSize() > 10) queryPageBean.setPageSize(10);
        return itemService.search(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
    }

}
