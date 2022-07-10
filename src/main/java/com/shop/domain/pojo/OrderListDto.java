package com.shop.domain.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class OrderListDto {
    private String orderId;
    private Date orderTime;
    private String userName;
    private String userId;
    private int SumMoney;
    private int shippingStatus;
}
