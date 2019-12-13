package com.itheima.health.dao;

import com.itheima.health.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CategoryDao {

    @Insert("INSERT INTO t_category(name,image) VALUES(#{name},#{image})")
    void add(Category category);

    @Update("UPDATE t_category SET status = 1 WHERE id = #{value}")
    void InvalidById(Integer id);

    @Select("SELECT * FROM t_category WHERE id = #{value}")
    Category findCategoryById(Integer id);

    @Select("SELECT * FROM t_category WHERE status = 0")
    List<Category> findAll();

    void edit(Category category);

}
