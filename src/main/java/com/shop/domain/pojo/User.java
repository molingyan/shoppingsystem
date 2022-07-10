package com.shop.domain.pojo;

import lombok.Data;

import java.util.Set;

@Data
public class User {
    private String userId;
    private String userName;
    private String passWord;
    private String phone;
    private String email;
    private String salt;
    private int state;
}
