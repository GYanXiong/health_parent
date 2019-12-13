package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {

    Set<Role> findRolesByUserId(Integer userId);

    void add(Role role);

    void addRoleAndPermissio(Map<String, Object> map);

    Page<Role> findByCondition(String queryString);

    Role findById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    void edit(Role role);

    void deleteRoleAndPermissionByRoleId(Integer id);

    List<Role> findAll();

    void addRoleAndMenu(Map<String, Object> map);

    void deleteRoleAndMenuByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    void deleteById(Integer id);

    long findRoleAndPermissionCountByRoleId(Integer id);

    long findRoleAndMenuCountByRoleId(Integer id);
}
