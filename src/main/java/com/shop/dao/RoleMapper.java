package com.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.domain.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {


    @Select("select * from role")
    public List<Role> getRole();



    @Select("select r.* from user u,role r,user_role ur where u.u_id = ur.user_id and r.role_id = ur.role_id and u.u_id=#{uId}")
    public List<Role> getUserRoles(String uId);



    @Select("select * from role")
    public List<Role> getRoleList();

    @Select("select * from role where role_id =#{roleId}")
    public Role getRoleById(String uId);

    @Insert("insert into role value(#{roleName})")
    public int addRole(Role role);

    @Delete("delete from role where role_id =#{roleId}")
    public int deleteRole(String roleId);

    @Update("update role set role_name=#{roleName} where role_id=#{roleId} ")
    public int updateRole(Role role);

    @Select("select * from role where role_name =#{roleName}")
    public Role getRoleByName(String roleName);


    @Select("select * from role u,role r,role_role ur where u.u_id = ur.role_id and r.role_id = ur.role_id")
    public List<Role> getRoleRoleList();


}
