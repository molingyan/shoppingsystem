package com.shop.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shop.R.Result;
import com.shop.domain.dto.UserDto;
import com.shop.domain.pojo.User;
import com.shop.log.MyLog;
import com.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Api(tags = {"用户接口"})
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    *
    * 根据用户ID查询
    * */
    @MyLog(value = "根据用户ID查询用户")
    @ApiOperation("根据用户ID查询用户")
    @GetMapping("/uid/{userId}")
    public Result getUserById(@PathVariable String userId) {
        Result result = userService.getUserById(userId);
        return result;
    }

    /*
     *
     * 根据用户名查询
     * */

    @MyLog(value = "根据用户名查询用户")
    @ApiOperation("根据用户名查询用户")
    @GetMapping("/name/{username}")
    public Result getUserByName(@PathVariable String username) {
        Result result = userService.getUserByName(username);
        return result;

    }


    /*
     *
     * 添加新用户
     * */
    @MyLog(value = "添加新用户")
    @ApiOperation("添加新用户")
    @PostMapping("/add")
    public Result addUser(@RequestBody User user) {
        Result result = userService.addUser(user);
        return result;
    }



    /*
     *
     * 修改用户信息
     * */
    @MyLog(value = "修改用户信息")
    @ApiOperation("修改用户信息")
    @PutMapping("/update")
    public Result update(@RequestBody UserDto userDto) {
        User user = Convert.convert(User.class, userDto);
        Result result = userService.updateUser(user);
        return result;
    }

    /*
     *
     * 删除用户
     *
    * */
    @MyLog(value = "删除用户")
    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{userId}")
    public Result deleteUser(@PathVariable String userId) {
        Result result = userService.deleteUser(userId);
        return result;
    }

    /*
    *
    * 获取用户列表（全部用户）
    *
    * */
    @MyLog(value = "获取全部用户")
    @ApiOperation("获取全部用户")
    @GetMapping("/getList")
    public Result getUserList() {
        List<UserDto> userList = userService.getUserList();
        return Result.success(userList);
    }


    /*
    *
    * 修改用户密码
    * */
    @MyLog(value = "修改密码")
    @ApiOperation("修改密码")
    @PutMapping("/updatePassWord")
    public Result updatePassWord(@RequestBody User user) {
        Result result = userService.updatePassWord(user.getUserId(), user.getPassWord());
        return result;
    }


    /*
    *
    * 分页查询
    * */
    @ApiOperation("获取全部用户、分页查询、模糊查询")
    @GetMapping("/{currentPage}/{pageSize}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, User user) {
        IPage<UserDto> page = userService.getPage(currentPage, pageSize, user);
        //如果当前页码值大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = userService.getPage((int) page.getPages(), pageSize, user);
        }
        return Result.success(page);
    }

}
