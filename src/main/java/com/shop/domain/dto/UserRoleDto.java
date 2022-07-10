package com.shop.domain.dto;

import lombok.Data;

@Data
public class UserRoleDto {
    private String uId;
    private String userName;
    private String passWord;
    private String phone;
    private String email;
    private int RoleId;
    private String RoleName;
}
