package com.shop.controller;
import com.shop.R.Result;
import com.shop.domain.pojo.User;
import com.shop.log.MyLog;
import com.shop.service.LoginService;
import com.shop.service.UserService;
import com.shop.utils.GetIPUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"登录接口"})
@CrossOrigin
@RequestMapping("/api")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;

    @ApiOperation("用户登录")


    @GetMapping("/returnStr/{Str}")
    public String returnString(@PathVariable String Str) {
    return Str;
    }

/*

    @PostMapping("/jwtlogin")
    public Result login(@RequestBody User user, HttpServletResponse rsp) {
        if (user.getUserName() == null) {
            return Result.error("用户名不能为空");
        } else if (user.getPassWord() == null) {
            return Result.error("密码不能为空");
        }
        User loginuser = userService.getUserByName(user.getUserName());
        if (loginuser==null){
            return Result.error("用户名不存在");
        }else if(!loginuser.getPassWord().equals(user.getPassWord())){
            return Result.error("密码错误");
        }
        String token = JwtUtils.sign(user.getSUserName(), String.valueOf(System.currentTimeMillis()));

        return new Result(ResultCode.SUCCESS,token);
    }
*/
    @MyLog(value = "用户登录")
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        Result result = loginService.login(user);
        return result;
    }

    @MyLog(value = "用户退出")
    @ApiOperation("用户登出")
    @RequestMapping("/logout")
    public Result logout() {
        return loginService.logout();
    }
}
