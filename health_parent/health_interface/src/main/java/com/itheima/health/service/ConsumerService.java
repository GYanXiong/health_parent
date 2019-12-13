package com.itheima.health.service;

import com.itheima.health.pojo.Consumer;

public interface ConsumerService {
    void add(Consumer consumer);

    void edit(Consumer consumer);

    Consumer findByIdAndPass(Integer id,String randPass);

    Consumer findByOpenId(String openid);

    String findOpenid(Integer id,String randPass);
}
