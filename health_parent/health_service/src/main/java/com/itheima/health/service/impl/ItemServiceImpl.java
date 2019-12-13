package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.RedisConstant;
import com.itheima.health.dao.ItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Item;
import com.itheima.health.service.ConsumerService;
import com.itheima.health.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service(interfaceClass = ItemService.class)
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private JedisPool jedisPool;

    public Item findById(Integer id) {
        return itemDao.findItemById(id);
    }

    public void add(Item item) {
        itemDao.addItem(item);
        Jedis resource = jedisPool.getResource();
        resource.sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCE,item.getImage());
        resource.close();
    }

    public void invalidById(Integer id) {
        itemDao.InvalidById(id);
    }

    public PageResult search(int currentPage, int pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = itemDao.search(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public void unInvalidById(Integer id) {
        itemDao.unInvalidById(id);
    }

    public void edit(Item item) {
        itemDao.edit(item);
    }

    public PageResult findByCondition(int currentPage, int pageSize, String queryString) {
        PageHelper.startPage(currentPage, pageSize);
        Page page = itemDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }
}
