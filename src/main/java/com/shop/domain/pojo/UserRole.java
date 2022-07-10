package com.shop.domain.pojo;

import lombok.Data;

@Data
public class UserRole {
    private String uId;
    private String userName;
    private String passWord;
    private String phone;
    private String email;
    private int RoleId;
    private String RoleName;

}
