package com.shop.domain.dto;

import com.shop.domain.pojo.Goods;
import lombok.Data;

@Data
public class OrderGoodsCount {
    private String goodsId;
    private String goodsName;
    private int amount;
}