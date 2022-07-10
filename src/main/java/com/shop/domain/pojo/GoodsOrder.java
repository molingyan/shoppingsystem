package com.shop.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodsOrder {
   private String orderId;
   private  String goodsId;
   private int amount;
}
