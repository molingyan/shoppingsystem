package com.shop.domain.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {
    private String goodsId;
    private String goodsName;
    private String goodsType;
    private String merchantId;
    private int goodsPrice;
    private String goodsImg;
    private String goodsDetails;
    private String goodsInventory;
    private String goodsState;
    private String consigneeId;
    private Date createDate;
    private Date updateDate;
}
