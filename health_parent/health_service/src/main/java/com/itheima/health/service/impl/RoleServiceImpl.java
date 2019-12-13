package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public void add(Role role, Integer[] permissionIds, Integer [] menuIds) {
        // 1:基本信息【保存】 向角色表新增一条数据，同时将新增数据的角色的id封装到权限中的id属性
        roleDao.add(role);
        // 2:权限信息【保存】 根据所选择的权限数组（int），和角色的id，向t_role_permission表新增数据
        setRoleAndPermission(role.getId(),permissionIds);

        // 3:菜单信息【保存】 根据所选择的菜单数组（int），和角色的id，向t_role_menu表新增数据
        setRoleAndMenu(role.getId(),menuIds);

    }

    // 根据所选择的权限数组（int），和角色的id，向t_role_permission表新增数据
    private void setRoleAndPermission(Integer roleId, Integer[] permissionIds) {
        if(permissionIds!=null && permissionIds.length>0){
            for (Integer permissionId : permissionIds) {
                // 方案二：使用Map
                Map<String,Object> map = new HashMap<>();
                map.put("roleId",roleId);
                map.put("permissionId",permissionId);
                roleDao.addRoleAndPermissio(map);
            }
        }
    }

    // 根据所选择的菜单数组（int），和角色的id，向t_role_menu表新增数据
    private void setRoleAndMenu(Integer roleId, Integer[] menuIds) {
        if(menuIds!=null && menuIds.length>0){
            for (Integer menuId : menuIds) {
                // 方案二：使用Map
                Map<String,Object> map = new HashMap<>();
                map.put("roleId",roleId);
                map.put("menuId",menuId);
                roleDao.addRoleAndMenu(map);
            }
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 1：初始化PageHelper
        PageHelper.startPage(currentPage,pageSize);
        // 2：查询
        Page<Role> page = roleDao.findByCondition(queryString);
        // 3：封装结果数据PageResult
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }

    @Override
    public void edit(Role role, Integer[] permissionIds, Integer [] menuIds) {
        // 一：根据角色页面填写情况，更新角色数据，更新t_role
        roleDao.edit(role);
        // 二：操作中间表数据t_role_permission
        // 1：使用角色的id，先删除中间表数据
        roleDao.deleteRoleAndPermissionByRoleId(role.getId());
        // 2：使用角色id和传递的权限id的数组，向中间表中添加数据
        this.setRoleAndPermission(role.getId(),permissionIds);

        // 3：使用角色的id，先删除中间表数据
        roleDao.deleteRoleAndMenuByRoleId(role.getId());
        // 4：使用角色id和传递的菜单id的数组，向中间表中添加数据
        this.setRoleAndMenu(role.getId(),menuIds);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    @Override
    public void deleteById(Role role, Integer id) {
        // 使用权限的id，查询中间表，判断是否存在数据
        long count = roleDao.findRoleAndPermissionCountByRoleId(id);
         count = roleDao.findRoleAndMenuCountByRoleId(id);
        // 存在数据
        if(count>0){
            throw new RuntimeException("当前角色在中间表中存在数据不能删除！");
        }
        roleDao.deleteById(id);

    }
}
