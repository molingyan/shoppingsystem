package com.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.R.Result;
import com.shop.config.exceptionResolver.UserException;
import com.shop.dao.ConsigneeMapper;
import com.shop.dao.RoleMapper;
import com.shop.dao.UserMapper;
import com.shop.dao.UserRoleMapper;
import com.shop.domain.dto.UserDto;
import com.shop.domain.pojo.User;
import com.shop.service.UserService;
import com.shop.utils.Md5;
import com.shop.utils.IdUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private ConsigneeMapper consigneeMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper redisJsonMapper = new ObjectMapper();


    @Override
    public Result getUserById(String userId) {

        User user;
        String userStr = stringRedisTemplate.opsForValue().get("user:userId:user:" + userId);
        try {
            if (userStr != null) {
                user = redisJsonMapper.readValue(userStr, User.class);
            } else {
                user = userMapper.getUserById(userId);
                userStr = redisJsonMapper.writeValueAsString(user);
                stringRedisTemplate.opsForValue().set("user:userId:user:" + userId, userStr, 600, TimeUnit.MINUTES);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (user == null) {
            return Result.error("用户不存在");
        }
        List<String> roles = userRoleMapper.getUserRoleByUserId(userId);
        UserDto userDto = Convert.convert(UserDto.class, user);
        userDto.setRoles(roles);
        return Result.success(userDto);

    }


    @Transactional
    @Override
    public Result addUser(User user) {
        if (user.getUserName() == null || user.getPassWord() == null) {
            return Result.error("用户名和密码不能为空");
        }
        if (userMapper.getUserByName(user.getUserName()) != null) {
            return Result.error("用户已存在，添加失败");
        }
        String userId = IdUtils.getUserId(stringRedisTemplate);
        user.setUserId(userId);
        String salt = Md5.getRandomSalt();
        SimpleHash md5PassWord = new SimpleHash("Md5", user.getPassWord(), salt, 1021);
        user.setSalt(salt);
        user.setPassWord(md5PassWord.toString());
        user.setState(1);
        if (userMapper.addUser(user) < 0 || userRoleMapper.addRoleUserById(user.getUserId()) < 0) {
            return Result.error("添加失败");
        } else {
            return Result.success("添加成功");
        }

    }

    /*修改用户密码*/
    @Override
    @Transactional
    public Result updatePassWord(String userId, String password) {
        String salt = Md5.getRandomSalt();
        SimpleHash md5PassWord = new SimpleHash("Md5", password, salt, 1021);
        User user = userMapper.getUserById(userId);
        if (user == null) return Result.error("用户不存在");
        userMapper.updatePassWord(userId, md5PassWord.toString(), salt);
        return Result.success("修改成功");
    }


    @Override
    public List<UserDto> getUserList() {
        List<User> userList = userMapper.getUserList();
        List<UserDto> userDtos = Convert.toList(UserDto.class, userList);
        for (int i = 0; i < userDtos.size(); i++) {
            List<String> roleList = userRoleMapper.getUserRoleByUserId(userDtos.get(i).getUserId());
            userDtos.get(i).setRoles(roleList);
        }
        return userDtos;
    }

    @Override
    public IPage<UserDto> getPage(int currentPage, int pageSize, User user) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<User>();
        lqw.like(Strings.isNotEmpty(user.getUserName()), User::getUserName, user.getUserName());
        lqw.like(Strings.isNotEmpty(user.getUserId()), User::getUserId, user.getUserId());
        Page<User> page = userMapper.selectPage(new Page<>(currentPage, pageSize), lqw);
        IPage<UserDto> iPage = page.convert(u -> {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(u, dto);
            return dto;
        });
        return iPage;
    }


    @Transactional
    @Override
    public Result deleteUser(String userId) {
        stringRedisTemplate.delete("user:userId:user:" + userId);
        userRoleMapper.deleteUserRoleByUserId(userId);
        consigneeMapper.deleteConsigneeByUId(userId);
        if (userMapper.deleteUser(userId) <= 0)
            return Result.error("删除失败");
        return Result.success("删除成功");
    }


    @Override
    public Result updateUser(User user) {
        stringRedisTemplate.delete("user:userId:user:" + user.getUserId());
        if (userMapper.updateUser(user) < 0)
            return Result.error("修改失败");
        else {
            return Result.success("修改成功");
        }

    }

    /*
     *
     * 根据用户名查找用户
     *
     * */
    @Override
    public Result getUserByName(String userName) {
        User user = userMapper.getUserByName(userName);
        if (user == null) {
            return Result.error("用户不存在");
        }
        List<UserDto> userDtos = Convert.toList(UserDto.class, user);
        /*        UserDto userDto = Convert.convert(UserDto.class, user);*/
        return Result.success(userDtos);
    }


    /*
     * 非前端调用
     * */
    @Override
    public User getUserByUserName(String username) {
        User user = userMapper.getUserByName(username);
        return user;
    }


}
