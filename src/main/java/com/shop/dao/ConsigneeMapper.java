package com.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.domain.pojo.Consignee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConsigneeMapper extends BaseMapper<Consignee> {

    @Select("select * from consignee where user_id = #{userId}")
    public List<Consignee> getConsigneesByUserId(String userId);

    @Select("select * from consignee where consignee_id =#{consigneeId}")
    public Consignee getConsigneeById(String consigneeId);

    @Insert("insert into consignee value(#{consigneeId},#{userId},#{consigneeAddr},#{consigneePhone},#{consigneeName},#{createDate},#{updateDate})")
    public int addConsignee(Consignee consignee);

    @Delete("delete from consignee where consignee_id =#{consigneeId}")
    public int deleteConsignee(String consigneeId);

    @Delete("delete from consignee where user_id =#{userId}")
    public int deleteConsigneeByUId(String userId);

    @Update("update consignee set consignee_name=#{consigneeName},consignee_phone=#{consigneePhone},consignee_Name=#{consigneeName},update_date=#{updateDate},consignee_addr=#{consigneeAddr} where consignee_id=#{consigneeId}")
    public int updateConsignee(Consignee consignee);

    @Select("select * from consignee where consignee_phone =#{consigneePhone}")
    public List<Consignee> getConsigneeByPhone(String consigneePhone);
}

