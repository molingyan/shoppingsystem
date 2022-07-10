
package com.shop.config.shiropackage;

import com.shop.config.shiro.JwtFilter;
import org.apache.catalina.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //shiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getshiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(defaultWebSecurityManager);

        /*设置过滤器*/
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new UserFilter());
        filters.put("roles", new UserFilter());
        factoryBean.setFilters(filters);


        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //开放登陆接口
        filterChainDefinitionMap.put("/api/login", "anon");
        filterChainDefinitionMap.put("/api/returnStr/**", "anon");
        filterChainDefinitionMap.put("/user/**", "authc");
        //拦截测试
        filterChainDefinitionMap.put("/api/menu/getMenuList", "roles[admin,user]");
//      针对Swagger拦截放行
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/configuration/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }




    //DafaulewebSecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myRealm") MyRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //管理userrealm
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    //创建对象
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    @Bean
    public SessionManager sessionManager() {
        //创建一个上面自定的SessionManager
        SessionManager mySessionManager = new SessionManager();
        return mySessionManager;
    }

}