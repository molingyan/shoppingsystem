package com.shop.service;

import com.shop.R.R;
import com.shop.domain.pojo.Menu;

public interface MenuService {
    public R getMenuList();

    public R addMenu(Menu menu);
}
