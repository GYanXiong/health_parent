package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author james
 * @Description
 * @Date 2019/12/13
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    /**
     * 获取今天和明天的预约信息, 使用数据库函数的sql实现
     *
     * @return 订单列表
     */
    @RequestMapping("/findPageByYesterdayAndToday")
    public PageResult findPageByYesterdayAndToday(@RequestBody QueryPageBean queryPageBean) throws Exception {
        return orderService.findPageByYesterdayAndToday(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
    }

    @RequestMapping("/updateForVisited")
    public Result updateForVisited(Integer id){
        try {
            orderService.updateForVisited(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPDATE_ORDER_FAIL);
        }
        return new Result(false, MessageConstant.UPDATE_ORDER_SUCCESS);
    }

    @RequestMapping("/updateForNotVisited")
    public Result updateForNotVisited(Integer id){
        try {
            orderService.updateForNotVisited(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.UPDATE_ORDER_FAIL);
        }
        return new Result(false, MessageConstant.UPDATE_ORDER_SUCCESS);
    }
}
