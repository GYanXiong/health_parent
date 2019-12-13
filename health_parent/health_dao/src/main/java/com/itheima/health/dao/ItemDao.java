package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface ItemDao {
    /*id,name,descript,status,img,category_code,price,
      #{id},#{name},#{descript},#{status},#{img},#{category_code},#{price},*/
    @Insert("INSERT INTO t_item(name,descript,status,image,category_id,price,count) VALUES(#{name},#{descript},#{status},#{image},#{category_id},#{price},#{count})")
    void addItem(Item item);

    @Update("UPDATE t_item SET status = 1 WHERE id = #{value}")
    void InvalidById(Integer id);

    @Update("UPDATE t_item SET status = 0 WHERE id = #{value}")
    void unInvalidById(Integer id);

    @Select("SELECT * FROM t_item WHERE id = #{value}")
    Item findItemById(Integer id);

    void edit(Item item);

    Page findByCondition(String queryString);

    Page search(String queryString);
}
