package com.shop.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRoleMapper {
    @Insert("insert into user_role value(null,#{uId},1) ")
    public int addRoleUserById(String uId);

    @Insert("insert into user_role value(#{uId},#{roleId}) ")
    public int addUserRole(String uId,int roleId);

    @Delete("delete from user_role where user_id =#{userId}")
    public int deleteUserRoleByUserId(String userId);

    @Delete("delete from user_role where role_id =#{roleId}")
    public int deleteUserRoleByRoleId(String roleId);

    @Delete("delete from user_role where user_id = #{userId} AND role_id =#{roleId}")
    public int deleteUserRoleById(String user_id,String roleId);

    @Select("select r.role_name from user u,role r,user_role ur where u.user_id = ur.user_id and r.role_id = ur.role_id and u.user_name=#{username} ")
    public List<String> getUserRoleByUserName(String username);

    @Select("select r.role_name from user u,role r,user_role ur where u.user_id = ur.user_id and r.role_id = ur.role_id and u.user_id=#{userId} ")
    public List<String> getUserRoleByUserId(String userId);


}
