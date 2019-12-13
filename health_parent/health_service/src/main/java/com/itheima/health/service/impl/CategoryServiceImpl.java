package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.CategoryDao;
import com.itheima.health.pojo.Category;
import com.itheima.health.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service(interfaceClass = CategoryService.class)
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private JedisPool jedisPool;

    public Category findById(Integer id) {
        return categoryDao.findCategoryById(id);
    }

    public void add(Category category) {
        categoryDao.add(category);
        Jedis resource = jedisPool.getResource();
        resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCE,category.getImage());
        resource.close();
    }

    public void delete(Integer id) {
        categoryDao.InvalidById(id);
    }

    public void edit(Category category) {
        categoryDao.edit(category);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
