package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User findUserByUsername(String username);

    void add(User user);

    Page<User> findByCondition(String queryString);

    void addUserAndRole(Map map);

    void edit(User user);

    void deleteUserAndRoleByUserId(Integer id);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void deleteById(Integer id);

    long findUserAndRoleCountByUserId(Integer id);
}
