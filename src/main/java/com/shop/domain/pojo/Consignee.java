package com.shop.domain.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Consignee {
    private String consigneeId;
    private String userId;
    private String consigneeAddr;
    private String consigneePhone;
    private String consigneeName;
    private Date createDate;
    private Date updateDate;
}
