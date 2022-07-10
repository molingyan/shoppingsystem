package com.shop.service.impl;

import com.shop.R.R;
import com.shop.dao.MenuMapper;
import com.shop.domain.pojo.Menu;
import com.shop.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public R getMenuList() {
        List<Menu> menus;
        Set roles = (Set) SecurityUtils.getSubject().getSession().getAttribute("roles");
        if (roles.contains("admin"))
        {
            menus= menuMapper.getMenuList();
        }else {
            menus= menuMapper.getUserMenuList();
        }
        traverseMenu(menus);
        return new R(menus,"--获取列表--");
    }

    @Override
    public R addMenu(Menu menu) {
        int i = menuMapper.addMenu(menu);
        return new R(i,i>0);
    }



    public List<Menu> traverseMenu(List<Menu> menuList) {
        for (Menu menu : menuList) {
            if (menu.getLevel()==3)break;
            menuList = menuMapper.getMenuListByhId(menu.getMId());
            System.out.println(menuList);
            menu.setMenus(menuList);
            System.out.println(menu);
            traverseMenu(menuList);
        }
        return menuList;
    }


}
