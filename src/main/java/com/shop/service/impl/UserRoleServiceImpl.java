package com.shop.service.impl;

import com.shop.dao.UserRoleMapper;
import com.shop.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    /*
     *
     * 根据用户名获取某个用户角色列表
     * */
    @Override
    public List<String> getUserRoleByUserName(String username) {
        List<String> roles = userRoleMapper.getUserRoleByUserName(username);
        return roles;
    }

    /*
     *
     * 根据用户ID获取某个用户角色列表
     * */
    @Override
    public List<String> getUserRoleById(String uid) {
        List<String> roles = userRoleMapper.getUserRoleByUserId(uid);
        return roles;
    }



}
