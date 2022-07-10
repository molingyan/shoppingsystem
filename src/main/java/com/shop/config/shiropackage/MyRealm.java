package com.shop.config.shiropackage;

import cn.hutool.core.convert.Convert;
import com.shop.domain.pojo.User;
import com.shop.service.UserRoleService;
import com.shop.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    /*
     * 授权
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行-->授权");
        User user = (User) principalCollection.getPrimaryPrincipal();
        List<String> roleList = userRoleService.getUserRoleByUserName(user.getUserName());
        Set<String> roleSet = Convert.toSet(String.class, roleList);
        if (roleSet != null) {
            roleSet = Convert.toSet(String.class, roleSet);
            System.out.println("roleSet=" + roleSet.toString());
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roleSet);
        SecurityUtils.getSubject().getSession().setAttribute("roles", roleSet);
        return authorizationInfo;
    }

    /*
    /*
     *
     * 认证
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行-->认证");
        //认证
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        User user = userService.getUserByUserName(username);
        /*
        * 判断用户是否存在
        * */
        if (user == null) {
            return null;
        }
        String passwordStr = String.valueOf(usernamePasswordToken.getPassword());
        SimpleHash md5PassWord = new SimpleHash("Md5", passwordStr, user.getSalt(), 1021);
        /*
        * 判断MD5是否正确
        * */
        if (!md5PassWord.toString().equals(user.getPassWord())) {
            throw new IncorrectCredentialsException();
        }
        /*
        * 判断用户状态是否正常
        *
        * */
        if (user.getState()!=1){
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,passwordStr, getName());
        return simpleAuthenticationInfo;
    }
}
