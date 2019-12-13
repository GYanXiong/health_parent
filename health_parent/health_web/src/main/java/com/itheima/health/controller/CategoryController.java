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
public class CategoryController {


    @Reference
    private CategoryService categoryService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/findById")
    public Category findById(Integer id) {
        return categoryService.findById(id);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Category category) {
        try {
            categoryService.edit(category);
            Jedis resource = jedisPool.getResource();
            resource.del(RedisConstant.CATEGORY_LIST_RESOURCE);
            resource.close();
            return new Result(true, "修改分类成功");
        } catch (Exception e) {
            return new Result(false, "修改分类失败");
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Category category) {
        try {
            categoryService.add(category);
            Jedis resource = jedisPool.getResource();
            resource.del(RedisConstant.CATEGORY_LIST_RESOURCE);
            resource.close();
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/delete")
    public Result edit(Integer id) {
        try {
            categoryService.delete(id);
            Jedis resource = jedisPool.getResource();
            resource.close();
            resource.del(RedisConstant.CATEGORY_LIST_RESOURCE);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/findAll")
    public List<Category> findByCondition() {
        List<Category> categorys = categoryService.findAll();
        return categorys;
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
}
