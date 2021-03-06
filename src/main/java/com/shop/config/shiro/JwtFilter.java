package com.shop.config.shiro;

import com.shop.controller.LoginController;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.HttpMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends AccessControlFilter {

    //设置请求头中需要传递的字段名
    protected static final String AUTHORIZATION_HEADER = "Access-Token";

    /**
     * 表示是否允许访问，mappedValue就是[urls]配置中拦截器参数部分，
     * 如果允许访问返回true，否则false
     *
     * @param request:
     * @param response:
     * @param mappedValue:
     * @author cheetah
     * @date 2020/11/24
     * @return: boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    /**
     * 表示当访问拒绝时是否已经处理了，
     * 如果返回true表示需要继续处理，
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可
     *
     * @param request:
     * @param response:
     * @author cheetah
     * @date 2020/11/24
     * @return: boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        // 解决跨域问题
        if (HttpMethod.OPTIONS.toString().matches(req.getMethod())) {
            return true;
        }
        if (!isLoginAttempt(request, response)) {
            HttpServletResponse httpResponse= (HttpServletResponse) response;
            httpResponse.setStatus(302);
            httpResponse.setHeader("location", "http://localhost:8082/api/returnStr/token is Null");
            return false;
        } else {
            //生成jwt token
            JwtToken token = new JwtToken(req.getHeader(AUTHORIZATION_HEADER));
            //委托给Realm进行验证
            try {
                //调用登陆会走Realm中的身份验证方法
                getSubject(request, response).login(token);
            } catch (Exception e) {
                HttpServletResponse httpResponse= (HttpServletResponse) response;
                httpResponse.setStatus(302);
                httpResponse.setHeader("location", "http://localhost:8082/api/returnStr/token is error");
                return false;
            }
        }
        return true;
        /*try {
            response.getWriter().write("token为空");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }


    /**
     * 判断是否有头部参数
     *
     * @param request:
     * @param response:
     * @author cheetah
     * @date 2020/11/24
     * @return: boolean
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION_HEADER);
        return authorization != null;
    }

}
