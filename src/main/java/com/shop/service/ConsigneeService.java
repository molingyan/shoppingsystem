package com.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.R.Result;
import com.shop.domain.pojo.Consignee;

public interface ConsigneeService extends IService<Consignee> {
    public Result getConsigneeById(String consigneeId);

    public Result addConsignee(Consignee consignee);

    public Result deleteConsignee(String consigneeId);
    public Result updateConsignee(Consignee consignee);
    public Result getConsigneeByName(String phone);
    public Result getConsigneesByUserId(String userId);
}

