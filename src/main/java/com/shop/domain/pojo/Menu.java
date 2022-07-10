package com.shop.domain.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private String mId;
    private String authName;
    private String path;
    private String higherId;
    private String icon;
    private int level;
    List<Menu> menus;
}
