package com.itheima.health.constant;

/**
 * @ClassName RedisConstant
 * @Description TODO
 * @Author ly
 * @Company 深圳黑马程序员
 * @Date 2019/12/1 11:01
 * @Version V1.0
 */
public class RedisConstant {
    // 上传七牛云的时候，采用的Redis的key
    public static final String SETMEAL_PIC_RESOURCE = "setmealPicResource";

    // 保存套餐的时候，采用Redis的key
    public static final String SETMEAL_PIC_DB_RESOURCE = "setmealPicDbResource";

    // 使用订单id，查询订单的详情的key
    public static final String ORDER_HASH_RESOURCE =  "healthOrderMap";

    // 使用套餐id，查询套餐的详情的key
    public static final String SETMEAL_HASH_RESOURCE =  "healthSetmealMap";
    public static final String SETMEAL_LIST_RESOURCE =  "healthSetmealList";

    //使用商品分类的redis
    public static final String CATEGORY_LIST_RESOURCE =  "healthCategoryString";

    //使用商品详情的redis
    public static final String ITEM_LIST_RESOURCE =  "healthItemHash";
}
