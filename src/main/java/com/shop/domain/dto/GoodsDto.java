package com.shop.domain.dto;

import lombok.Data;

@Data
public class GoodsDto {
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private Double goodsPrice;
    private String goodsImg;
    private String goodsDetails;
    private String goodsInventory;
    private String goodsState;
}
