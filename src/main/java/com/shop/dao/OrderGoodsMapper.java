package com.shop.dao;

import com.shop.domain.dto.OrderGoodsCount;
import com.shop.domain.pojo.GoodsOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderGoodsMapper {

    @Select("SELECT goods_id,amount from `order_goods` where order_id = #{orderId}")
    public List<OrderGoodsCount> getGoodsCount(String orderId);


    @Insert("insert into order_goods value (null,#{orderId},#{goodsId},#{amount})")
    public int addOrderGoods(GoodsOrder goodsOrder);
}
