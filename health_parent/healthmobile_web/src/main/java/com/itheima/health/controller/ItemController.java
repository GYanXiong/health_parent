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
public class ItemController {

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

    @RequestMapping("/edit")
    public Result edit(@RequestBody Item item) {
        try {
            itemService.edit(item);
            Jedis resource = jedisPool.getResource();
            resource.hdel(RedisConstant.ITEM_LIST_RESOURCE,item.getId()+"");
            resource.close();
            return new Result(true, "修改商品信息成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改商品信息失败");
        }
    }

    @RequestMapping("/invalidById")
    public Result invalidById(Integer id) {
        try {
            itemService.invalidById(id);
            Jedis resource = jedisPool.getResource();
            resource.hdel(RedisConstant.ITEM_LIST_RESOURCE,id+"");
            resource.close();
            return new Result(true, "下架成功");
        } catch (Exception e) {
            return new Result(false, "下架失败");
        }
    }

    @RequestMapping("/unInvalidById")
    public Result unInvalidById(Integer id) {
        try {
            itemService.unInvalidById(id);
            Jedis resource = jedisPool.getResource();
            resource.hdel(RedisConstant.ITEM_LIST_RESOURCE,id+"");
            resource.close();
            return new Result(true, "上架成功");
        } catch (Exception e) {
            return new Result(false, "上架失败");
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Item item) {
        try {
            itemService.add(item);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/findPage")
    public PageResult findByCondition(@RequestBody QueryPageBean queryPageBean) {
        if (queryPageBean.getPageSize() > 10) queryPageBean.setPageSize(10);
        return itemService.findByCondition(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            filename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
            QiniuUtils.upload2Qiniu(file.getBytes(), filename);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCE, filename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, filename);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody QueryPageBean queryPageBean) {
        if (queryPageBean.getPageSize() > 10) queryPageBean.setPageSize(10);
        return itemService.search(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
    }

}
