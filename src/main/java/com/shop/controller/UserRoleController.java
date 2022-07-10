package com.shop.controller;

import cn.hutool.core.convert.Convert;
import com.shop.R.Result;
import com.shop.domain.pojo.Role;
import com.shop.service.UserRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("/userRole")
@RestController
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;



    @ApiOperation("根据用户id查询用户权限")
    @GetMapping("/{userId}")
    public Result getUserRoleByuId(@PathVariable String userId) {
        List<String> roleList = userRoleService.getUserRoleById(userId);
        return Result.success(roleList);

    }


    @ApiOperation("根据用户名查询用户权限")
    @GetMapping("name/{userName}")
    public Result getUserRoleByUserName(@PathVariable String userName) {
        List<String> roleList =userRoleService.getUserRoleByUserName(userName);
        return Result.success(roleList);
    }

}
