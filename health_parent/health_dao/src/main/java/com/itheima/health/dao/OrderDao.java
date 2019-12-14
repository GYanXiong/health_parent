package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderList;
import org.apache.ibatis.annotations.Param;

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

    Page<OrderList> findPageByYesterdayAndToday(@Param("queryString") String queryString, @Param("start") String start, @Param("end") String end);

    void updateForVisited(Integer id);

    void updateForNotVisited(Integer id);
}
