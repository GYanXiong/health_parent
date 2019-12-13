package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CheckItemController
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/11/28 10:04
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Reference
    UserService userService;

    // 从SpringSecurity中获取用户信息，显示username对应的模型中
    @RequestMapping(value = "/getUsername")
    public Result getUsername(){
        try {
            // 使用SpringSecurity的方式完成
            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            // 使用登录名，查询用户信息，返回用户对象
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }

    // 新增用户
    @RequestMapping(value = "/add")
    public Result add(@RequestBody com.itheima.health.pojo.User user, Integer [] roleIds){
        try {
            userService.add(user,roleIds);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }

    // 分页查询用户列表
    @RequestMapping(value = "/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = userService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    // 编辑用户
    @RequestMapping(value = "/edit")
    public Result edit(@RequestBody com.itheima.health.pojo.User user ,Integer [] roleIds){
        try {
            userService.edit(user,roleIds);
            return new Result(true, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败");
        }
    }

    // ID查询用户
    @RequestMapping(value = "/findById")
    public Result findById(Integer id){
        com.itheima.health.pojo.User user = userService.findById(id);
        if(user!=null){
            return new Result(true, "查询成功",user);
        }
        else{
            return new Result(false,"查询失败");
        }
    }

    // 删除角色
    @RequestMapping(value = "/deleteById")
    public Result deleteById(@RequestBody com.itheima.health.pojo.User user, Integer id){
        try {
            userService.deleteById(user,id);
            return new Result(true, "删除成功");
        } catch(RuntimeException e){
            return new Result(false,e.getMessage());
        } catch(Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

    // 使用用户ID，查询角色的ID集合
    @RequestMapping(value = "/findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id){
        List<Integer> list  = userService.findRoleIdsByUserId(id);
        return list;
    }
}
