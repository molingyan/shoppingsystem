package com.shop.dao;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shop.domain.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    public List<User> getUserList();

    @Select("select * from user where user_id =#{userId}")
    public User getUserById(String userId);

    @Select("select user_name from user where user_id =#{userId}")
    public String getUserNameById(String userId);
    @Insert("insert into user value(null,#{userId},#{userName},#{passWord},#{phone},#{email},#{salt},#{state})")
    public int addUser(User user);

    @Delete("delete from user where user_id =#{userId}")
    public int deleteUser(String userId);

    @Update("update user set phone=#{phone},email=#{email},state=#{state} where user_id=#{userId} ")
    public int updateUser(User user);

    @Select("select * from user where user_name =#{username}")
    public User getUserByName(String username);



    @Update("update user set pass_word=#{passWord},salt=#{salt} where user_id=#{userId} ")
    public int updatePassWord(String userId,String passWord,String salt);


}
