package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.MenuDao;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.Menu;
import com.itheima.health.pojo.Role;
import com.itheima.health.pojo.User;
import com.itheima.health.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.ArrayList;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Menu> getMenus(String username) {
        //根据用户名获取用户
        User user = userDao.findUserByUsername(username);
        // System.out.println(user.getId() + "---" + user);
        //获取用户拥有的角色
        Set<Role> roles = roleDao.findRolesByUserId(user.getId());
        Set<Menu> menus = new HashSet<>();

        for (Role role : roles) {
            // System.out.println(role.getName());
            //获取父标题
            List<Menu> ParentList = menuDao.findParentMenuByRoleId(role.getId());
            for (Menu PatentMenu : ParentList) {
                Set<Menu> son = new HashSet<>();
                List<Menu> SonList = null;
                for (Role role1 : roles) {
                    //获取子标题
                    SonList = menuDao.findSonMenuByRoleId(PatentMenu.getId(), role1.getId());
                    for (Menu menu : SonList) {
                        son.add(menu);
                    }
                }
                ArrayList<Menu> menus1 = new ArrayList<>();
                menus1.addAll(son);
                PatentMenu.setChildren(menus1);
                if (PatentMenu.getChildren().size() != 0)
                    menus.add(PatentMenu);
            }
        }

        List<Menu> list = new ArrayList<>();
        list.addAll(menus);

        Collections.sort(list, new Comparator<Menu>() {
            @Override
            public int compare(Menu o1, Menu o2) {
                Integer i = Integer.parseInt(o1.getPath());
                Integer ii = Integer.parseInt(o2.getPath());
                return i - ii ;
            }
        });

        return list;
    }
}
