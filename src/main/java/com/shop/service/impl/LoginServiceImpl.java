package com.shop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.R.Result;
import com.shop.dao.UserMapper;
import com.shop.domain.pojo.User;
import com.shop.service.LoginService;
import com.shop.utils.GetIPUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
@CrossOrigin
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final ObjectMapper toJsonMapper = new ObjectMapper();

    public Result login(User user){
        if (user.getUserName() == null) {
            return Result.error("用户名不能为空");
        } else if (user.getPassWord() == null) {
            return Result.error("密码不能为空");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassWord());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            String logintoken = (String) subject.getSession().getId();
            return Result.success(logintoken);
        } catch (UnknownAccountException e) {
            return Result.error("登录失败，该账号不存在！");
        } catch (LockedAccountException e) {
            return Result.error("该账号已冻结！");
        } catch (IncorrectCredentialsException e) {
            return Result.error("登录失败，密码错误！");
        } catch (AuthenticationException e) {
            return Result.error("登录异常，请稍后再试");
        }
    }

    @Override
    public Result logout() {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        return Result.success("已退出");
    }

    ;
}
