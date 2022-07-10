package com.shop.log;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface SysMapper {


    @Insert("insert into log value (#{id},#{userId},#{userName},#{operation},#{method},#{params},#{ip},#{createDate}) ")
    public int addSys(SysLog sysLog);

}
