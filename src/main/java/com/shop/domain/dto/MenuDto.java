package com.shop.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDto {
    private String mId;
    private String authName;
    private String path;
    private String higherId;
    private String icon;
    private int level;
}
