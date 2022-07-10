package com.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.domain.pojo.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select("select * from goods")
    public List<Goods> getGoodsList();

    @Select("select goods_name from goods WHERE goods_id=#{goodsId}}")
    public String getGoodsNameById(String goodsId);


    @Select("select * from goods where goods_id =#{goodsId}")
    public Goods getGoodsById(String goodsId);

    @Insert("insert into goods value(null,#{goodsId},#{goodsName},#{goodsPrice},#{goodsType},#{goodsImg},#{goodsDetails},#{goodsInventory},#{goodsState},#{createDate},#{updateDate})")
    public int addGoods(Goods goods);

    @Delete("delete from goods where goods_id =#{goodsId}")
    public int deleteGoods(String goodsId);

    @Update("update goods set goods_name=#{goodsName},goods_type=#{goodsType},goods_price=#{goodsPrice},goods_img=#{goodsImg},goods_details=#{goodsDetails},goods_inventory=#{goodsInventory},goods_state=#{goodsState},update_date=#{updateDate}  where goods_id=#{goodsId}")
    public int updateGoods(Goods goods);

    @Select("select * from goods where goods_name =#{goodsName}")
    public Goods getGoodsByName(String name);

    @Select("select * from goods where goods_type =#{goodsType}")
    public List<Goods> getGoodsByTypeId(String goodsType);

    @Select("update goods set  goods_type =#{goodsType} where goods_id=#{goodsId}}")
    public int updateGoodsType(String goodsType,String goodsId);
}

