package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.Result;

import java.util.Map;

public interface OrderService {

    Result submitOrder(Map map);

    Map<String,Object> findById(Integer id);

    PageResult findPageByYesterdayAndToday(Integer currentPage, Integer pageSize, String queryString) throws Exception;

    void updateForVisited(Integer id);

    void updateForNotVisited(Integer id);
}
