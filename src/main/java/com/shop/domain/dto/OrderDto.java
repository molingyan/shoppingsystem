package com.shop.domain.dto;

import com.shop.domain.pojo.Consignee;
import com.shop.domain.pojo.Goods;
import com.shop.domain.pojo.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private String orderId;
    private Date orderTime;
    private UserDto user;
    private ConsigneeDto consignee;
    private  List<OrderGoodsCount> goodsList;
    private int orderStatus;
    private int sumMoney;
    private int paymentStatus;
    private Date updateTime;
}

