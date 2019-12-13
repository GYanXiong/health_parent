package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Item;

public interface ItemService {

    Item findById(Integer id);

    void add(Item item);

    void invalidById(Integer id);

    void unInvalidById(Integer id);

    void edit(Item item);

    PageResult findByCondition(int currentPage, int pageSize, String queryString);

    PageResult search(int currentPage, int pageSize, String queryString);
}
