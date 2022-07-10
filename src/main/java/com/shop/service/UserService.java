package com.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.R.Result;
import com.shop.domain.dto.UserDto;
import com.shop.domain.pojo.User;

import java.util.List;
import java.util.Set;

public interface UserService extends IService<User> {
    public Result getUserById(String uId);

    public Result addUser(User user);

    public List<UserDto> getUserList();
    public  IPage<UserDto> getPage(int currentPage, int pageSize, User user);

    public Result deleteUser(String uId);
    public Result updateUser(User user);
    public Result getUserByName(String userName);

    public User getUserByUserName(String username);


    public Result updatePassWord(String userId, String password);

}
