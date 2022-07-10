package com.shop.domain.dto;

import lombok.Data;

@Data
public class ConsigneeDto {
    private String consigneeId;
    private String userId;
    private String consigneeAddr;
    private String consigneePhone;
    private String consigneeName;
}
