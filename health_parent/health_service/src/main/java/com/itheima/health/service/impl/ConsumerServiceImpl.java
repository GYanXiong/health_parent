package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.ConsumerDao;
import com.itheima.health.pojo.Consumer;
import com.itheima.health.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = ConsumerService.class)
@Transactional
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerDao consumerDao;

    public void add(Consumer consumer) {
        consumerDao.add(consumer);
    }

    public void edit(Consumer consumer) {
        consumerDao.edit(consumer);
    }

    public Consumer findByIdAndPass(Integer id, String randPass) {
        return consumerDao.findByIdAndPass(id,randPass);
    }

    public Consumer findByOpenId(String openid) {
        return consumerDao.findByOpenid(openid);
    }

    public String findOpenid(Integer id, String randPass) {
        return consumerDao.findOpenid(id,randPass);
    }
}
