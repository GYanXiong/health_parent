package com.itheima.health.dao;

import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {

    List<Menu> findParentMenuByRoleId(Integer id);

    List<Menu> findSonMenuByRoleId(@Param("parentId") Integer id,@Param("roleId") Integer id1);

}
