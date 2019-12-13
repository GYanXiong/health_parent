package com.itheima.health.dao;

import com.itheima.health.pojo.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> findOrderListByCondition(Order order);

    void add(Order order);

    Map<String,Object> findById(Integer id);

    Integer findTodayOrderNumber(String today);

    Integer findTodayVisitsNumber(String today);

    Integer findThisOrderNumber(Map<String, Object> map);

    Integer findThisVisitsNumber(Map<String, Object> map);

    List<Map> findHotSetmeal();


}
