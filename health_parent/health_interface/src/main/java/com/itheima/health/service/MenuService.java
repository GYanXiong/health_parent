package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenus(String username);

    void add(Menu menu);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();
}
