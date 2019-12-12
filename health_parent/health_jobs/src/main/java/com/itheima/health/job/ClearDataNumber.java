package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.service.OrderSettingService;


import java.util.Date;

public class ClearDataNumber {

    @Reference
    private OrderSettingService orderSettingService;

    //删除历史预约设置
    public void deleteNumberByOrderDate() {
        System.out.println(new Date() + "执行历史数据预约设置清除");
        orderSettingService.deleteNumberByOrderDate();
        System.out.println(orderSettingService);
    }

}
