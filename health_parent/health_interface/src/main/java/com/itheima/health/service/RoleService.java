package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;

import java.util.List;

public interface RoleService {
    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    Role findById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);

    List<Role> findAll();

    List<Integer> findMenuIdsByRoleId(Integer id);

    void deleteById(Role role, Integer id);
}

