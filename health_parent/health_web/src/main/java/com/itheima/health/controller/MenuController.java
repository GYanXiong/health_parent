package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    /**
     * 根据用户权限加载页面菜单项
     */
    @RequestMapping("/getMenus")
    private Result getMenus() {
        //获取该登录的用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据用户的角色获取应有的权限列表
         List<Menu>  menu = menuService.getMenus(user.getUsername());

        if (menu.size() > 0) {
            return new Result(true, null, menu);
        } else {
            return new Result(false, MessageConstant.USER_NO_ROLE);
        }
    }

    // 新增菜单
    @RequestMapping(value = "/add")
    public Result add(@RequestBody Menu menu){
        try {
            menuService.add(menu);
            return new Result(true, "添加菜单成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加菜单失败");
        }
    }

    // 分页查询菜单列表
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = menuService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    // 删除菜单
    @RequestMapping(value = "/deleteById")
    public Result deleteById(Integer id){
        try {
            menuService.deleteById(id);
            return new Result(true, "删除成功");
        } catch(RuntimeException e){
            return new Result(false,e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    // ID查询菜单（回显）
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        Menu menu = menuService.findById(id);
        if(menu!=null){
            return new Result(true,"编辑成功",menu);
        }
        else{
            return new Result(false,"编辑失败");
        }
    }

    // 编辑菜单
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody Menu menu){
        try {
            menuService.edit(menu);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }
    }

    // 查询所有菜单
    @RequestMapping(value = "/findAll")
    public Result findAll() {
        List<Menu> list = menuService.findAll();
        if (list != null && list.size() > 0) {
            return new Result(true, "查询菜单成功", list);
        } else {
            return new Result(false, "查询菜单失败");
        }
    }
}
