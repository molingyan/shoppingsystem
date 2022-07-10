package com.shop.controller;

import com.shop.R.R;
import com.shop.domain.pojo.Menu;
import com.shop.log.MyLog;
import com.shop.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags={"菜单接口"})
@CrossOrigin
@RestController

@RequestMapping("/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;


    @ApiOperation("获取导航列表")
    @MyLog(value = "获取导航列表")
    @GetMapping("/getMenuList")
    public R getMenu() {
       R r = menuService.getMenuList();
       return r;
    }

    @MyLog(value = "新增菜单")
    @ApiOperation("新增菜单")
    @PostMapping("/addMenu")
    public R addMenu(@RequestBody Menu menu) {
       return menuService.addMenu(menu);
    }



}
