package com.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.domain.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    @Select("select * from type")
    public List<Type> getTypeList();

    @Select("select * from type where type_id =#{typeId}")
    public Type getTypeById(String typeId);

    @Insert("insert into type value(#{typeId},#{typeName},#{typeParent},#{typeLevel},#{typeState},#{createdDate},#{updateDate})")
    public int addType(Type type);

    @Delete("delete from type where type_id =#{typeId}")
    public int deleteType(String typeId);

    @Update("update type set type_name=#{typeName},type_parent=#{typeParent},type_level=#{typeLevel},type_state=#{typeState},update_date=#{updateDate} te where type_id=#{typeId}")
    public int updateType(Type type);

    @Select("select * from type where type_name =#{typeName}")
    public Type getTypeByName(String name);

    @Select("select * from type where type_parent=#{typeParent}")
    public List<Type> getListByPId(String typeParent);

    @Select("select * from type where type_level=#{typeLevel}")
    public List<Type> getListBylevel(int typeLevel);
}

