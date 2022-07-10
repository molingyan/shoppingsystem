package com.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.domain.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("select * from `order`")
    public List<Order> getOrderList();


/*    public List<Order> selectOrderList();*/
    @Select("select * from `order` where user_id = #{userId}")
    public List<Order> getOrderListByUserId(String userId);

    @Select("select * from `order` where order_consignee=#{orderConsigneeId}")
    public List<Order> getOrderListByConId(String orderConsigneeId);


    @Select("select * from `order` where goods_id=#{goodsId}")
    public List<Order> getOrderListByGoodsId(String goodsId);


    @Select("select * from `order` where order_id =#{orderId}")
    public Order getOrderById(String orderId);

    @Insert("insert into `order` value(#{orderId},#{orderTime},#{userId},0,#{consigneeId},0,#{updateTime})")
    public int addOrder(Order order);

    @Delete("delete from `order` where order_id =#{orderId}")
    public int deleteOrder(String orderId);


}

