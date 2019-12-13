package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;
import com.itheima.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckItemServiceImpl
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/11/28 10:03
 * @Version V1.0
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user;
    }

    @Override
    public void add(User user, Integer[] roleIds) {
        //1：保存用户，返回用户ID，封装到user中的id属性

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 保存到数据库，是一个加密的结果
        String s1 = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(s1);
        System.out.println(s1);

        userDao.add(user);

        //2：遍历循环，关联中间表
        if(roleIds!=null && roleIds.length>0){
            setUserAndRole(user.getId(),roleIds);
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 使用Page完成封装
        // 1：初始化分页的参数
        PageHelper.startPage(currentPage,pageSize);
        // 2：查询
        Page<User> page = userDao.findByCondition(queryString);
        // 3：封装PageResult数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void edit(User user,Integer [] roleIds) {
        // 一：根据用户页面填写情况，更新用户数据，更新t_user
        userDao.edit(user);
        // 二：操作中间表数据t_user_role
        // 1：使用用户的id，先删除中间表数据
        userDao.deleteUserAndRoleByUserId(user.getId());
        // 2：使用用户id和传递的角色id的数组，向中间表中添加数据
        this.setUserAndRole(user.getId(),roleIds);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Override
    public void deleteById(User user, Integer id) {
        // 使用用户的id，查询中间表，判断是否存在数据
        long count = userDao.findUserAndRoleCountByUserId(id);
        // 存在数据
        if(count>0){
            throw new RuntimeException("当前角色在中间表中存在数据不能删除！");
        }
        userDao.deleteById(id);
    }


    // 遍历循环，向用户和角色的中间表关联数据
    private void setUserAndRole(Integer userId, Integer[] roleIds) {
        for (Integer roleId : roleIds) {
            Map map = new HashMap();
            map.put("userId",userId);
            map.put("roleId",roleId);
            userDao.addUserAndRole(map);
        }
    }
}
