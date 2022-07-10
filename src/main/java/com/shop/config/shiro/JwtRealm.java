package com.shop.config.shiro;

import com.shop.service.UserService;
import com.shop.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;

public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //验证是不是自己的token类型
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     *  权限校验（次数不做过多讲解）
     * @author cheetah
     * @date 2020/11/25
     * @param principals:
     * @return: org.apache.shiro.authz.AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtils.getClaim(principals.toString(), "username");


        System.out.println("执行授权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermission("user:add");
        //角色权限暂时不加
//        authorizationInfo.setRoles(userService.getRoles(username));
//        authorizationInfo.setStringPermissions(userService.queryPermissions(username));
        //  authorizationInfo.setRoles(userService.getUserRoleByUId());
        //Set<String> roleSet = userService.getUserRoleByUserName(username);
        HashSet<String> set = new HashSet<>();
       set.add("admin");
        authorizationInfo.setRoles(set);
        return authorizationInfo;
    }



    /**
     * 身份验证
     *
     * @param token:
     * @author cheetah
     * @date 2020/11/25
     * @return: org.apache.shiro.authc.AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String credentials = (String) token.getCredentials();
        String username = null;
        try {
            //jwt验证token
            boolean verify = JwtUtils.verify(credentials);
            if (!verify) {
                throw new AuthenticationException("Token校验不正确");
            }
            username = JwtUtils.getClaim(credentials, JwtUtils.ACCOUNT);
        } catch (Exception e) {
            throw new AuthenticationException("Token校验异常");
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，不设置则使用默认的SimpleCredentialsMatcher
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                credentials, //凭证
                getName()  //realm name
        );
        return authenticationInfo;
    }



}
