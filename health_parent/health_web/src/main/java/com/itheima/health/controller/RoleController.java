package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import com.itheima.health.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    RoleService roleService;

    // 新增角色
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Role role, Integer [] permissionIds, Integer [] menuIds){
        try {
            roleService.add(role,permissionIds,menuIds);
            return new Result(true, "新增角色成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"新增角色失败");
        }
    }

    // 分页查询角色列表
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = roleService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    // ID查询角色
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        Role role = roleService.findById(id);
        if(role!=null){
            return new Result(true, "查询角色成功",role);
        }
        else{
            return new Result(false,"查询角色失败");
        }
    }

    // 使用角色ID，查询权限的ID集合
    @RequestMapping(value = "/findPermissionIdsByRoleId")
    public List<Integer> findPermissionIdsByRoleId(Integer id){
        List<Integer> list  = roleService.findPermissionIdsByRoleId(id);
        return list;
    }

    // 使用角色ID，查询菜单的ID集合
    @RequestMapping(value = "/findMenuIdsByRoleId")
    public List<Integer> findMenuIdsByRoleId(Integer id){
        List<Integer> list  = roleService.findMenuIdsByRoleId(id);
        return list;
    }

    // 编辑角色
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody Role role, Integer [] permissionIds, Integer [] menuIds){
        try {
            roleService.edit(role,permissionIds,menuIds);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }
    }

    // 删除角色
    @RequestMapping(value = "/deleteById")
    public Result deleteById(@RequestBody Role role, Integer id){
        try {
            roleService.deleteById(role,id);
            return new Result(true, "删除成功");
        } catch(RuntimeException e){
            return new Result(false,e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    // 查询所有角色
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        List<Role> list = roleService.findAll();
        if(list!=null && list.size()>0){
            return new Result(true, "查询角色成功",list);
        }
        else{
            return new Result(false,"查询角色失败");
        }
    }
}
