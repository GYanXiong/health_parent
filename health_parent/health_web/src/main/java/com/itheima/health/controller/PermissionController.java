package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import com.itheima.health.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    PermissionService permissionService;

    // 新增权限
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return new Result(true, "添加权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加权限失败");
        }
    }

    // 分页查询权限列表
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    // 删除权限
    @RequestMapping(value = "/deleteById")
    public Result deleteById(Integer id){
        try {
            permissionService.deleteById(id);
            return new Result(true, "删除成功");
        } catch(RuntimeException e){
            return new Result(false,e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    // ID查询权限（回显）
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        Permission permission = permissionService.findById(id);
        if(permission!=null){
            return new Result(true,"编辑成功",permission);
        }
        else{
            return new Result(false,"编辑失败");
        }
    }

    // 编辑权限
    @RequestMapping(value = "/edit")
    @PreAuthorize(value = "hasAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody Permission permission){
        try {
            permissionService.edit(permission);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }
    }

    // 查询所有权限
    @RequestMapping(value = "/findAll")
    public Result findAll(){
        List<Permission> list = permissionService.findAll();
        if(list!=null && list.size()>0){
            return new Result(true,"查询权限成功",list);
        }
        else{
            return new Result(false,"查询权限失败");
        }
    }
}
