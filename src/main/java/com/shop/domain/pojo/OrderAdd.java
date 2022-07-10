package com.shop.domain.pojo;

import com.shop.domain.dto.OrderGoodsCount;
import lombok.Data;

import java.util.List;

@Data
public class OrderAdd {
    private String userId;
    private String consigneeId;
    private List<OrderGoodsCount> goodsList;
    private int orderStatus;
    private int paymentStatus;
}
