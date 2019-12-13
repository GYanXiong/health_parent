package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<Menu> findParentMenuByRoleId(Integer id);

    List<Menu> findSonMenuByRoleId(@Param("parentId") Integer id,@Param("roleId") Integer id1);

    void add(Menu menu);

    Page<Menu> findByCondition(String queryString);

    long findRoleAndMenuCountByMenuId(Integer id);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();
}
