package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);

    void add(User user, Integer[] roleIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void edit(User user,Integer [] roleIds);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void deleteById(User user, Integer id);
}
