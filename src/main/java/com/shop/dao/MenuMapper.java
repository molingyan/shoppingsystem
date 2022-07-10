package com.shop.dao;

import com.shop.domain.pojo.Menu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select("select * from menu where higher_id = '10000' ")
    public List<Menu> getMenuList();

    @Select("select * from menu where higher_id = '20000' ")
    public List<Menu> getUserMenuList();

    @Select("select * from menu where higher_id=#{higherId}")
    public List<Menu> getMenuListByhId(String higherId);


    @Insert("INSERT  INTO menu values(#{mId},#{authName},#{path},#{higherId},#{icon},#{level})")
    public int addMenu(Menu menu);

}
