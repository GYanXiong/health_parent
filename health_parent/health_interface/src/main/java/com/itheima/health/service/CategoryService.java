package com.itheima.health.service;


import com.itheima.health.pojo.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Integer id);

    void add(Category category);

    void delete(Integer id);

    void edit(Category category);

    List<Category> findAll();
}
