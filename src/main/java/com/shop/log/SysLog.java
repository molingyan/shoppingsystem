package com.shop.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SysLog implements Serializable {
    private Long id;
    private String userId;
    private String userName; //用户名

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    private String ip; //ip地址

    private Date createDate; //操作时间
    //创建getter和setter方法
}