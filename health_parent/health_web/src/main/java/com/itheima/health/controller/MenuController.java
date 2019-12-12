package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import com.itheima.health.service.MenuService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    //根据用户权限加载页面菜单项
    @RequestMapping("/getMenus")
    private Result getMenus() {
        //获取该登录的用户
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //根据用户的角色获取应有的权限列表
        List<Menu> menu = menuService.getMenus(user.getUsername());

        if (menu.size() > 0) {
            return new Result(true, null, menu);
        } else {
            return new Result(false, MessageConstant.USER_NO_ROLE);
        }
    }


}
