package com.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.R.Result;
import com.shop.domain.dto.OrderDto;
import com.shop.domain.pojo.Order;
import com.shop.domain.pojo.OrderAdd;

import java.util.List;

public interface OrderService extends IService<Order> {


    public Result getOrderListByUserId(String userId);

    public Result getOrderListByConId(String orderConsignee);

    public Result getOrderListByGoodsId(String goodsId);

    public Result getOrderById(String orderId);

    public Result addOrder(OrderAdd orderAddBean);

    public Result getOrderList();

}

