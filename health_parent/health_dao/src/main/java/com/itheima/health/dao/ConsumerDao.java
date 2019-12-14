package com.itheima.health.dao;

import com.itheima.health.pojo.Consumer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ConsumerDao {

    Consumer findByIdAndPass(@Param("id") Integer id, @Param("randPass") String randPass);

    @Select("SELECT id,nickName,avatarUrl,gender,phoneNumber,openid,randPass From t_consumer where openid = #{openid}")
    Consumer findByOpenid(String openid);

    void add(Consumer consumer);

    void edit(Consumer consumer);

    @Select("SELECT openid from t_consumer WHERE id = #{id} and randPass = #{randPass}")
    String findOpenid(@Param("id") Integer id, @Param("randPass") String randPass);
}
