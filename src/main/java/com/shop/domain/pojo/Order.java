package com.shop.domain.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    private String orderId;
    private Date orderTime;
    private String userId;
    private int orderStatus;
    private int paymentStatus;
    private String consigneeId;
    private Date updateTime;
    private int sumMoney;
}

