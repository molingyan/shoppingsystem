package com.shop.controller;

import cn.hutool.core.convert.Convert;
import com.shop.R.Result;
import com.shop.domain.dto.ConsigneeDto;
import com.shop.domain.pojo.Consignee;
import com.shop.service.ConsigneeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags={"收货人信息接口"})
@CrossOrigin
@RestController
@RequestMapping("/consignee")
public class ConsigneeController {
    @Autowired
    private ConsigneeService consigneeService;


    @ApiOperation("根据收货人ID查询")
    @GetMapping("/consigneeId/{consigneeId}")
    public Result getConsigneeById(@PathVariable String consigneeId) {
        Result r = consigneeService.getConsigneeById(consigneeId);
        return r;
    }

    @ApiOperation("根据名字查询")
    @GetMapping("/name/{consigneeName}")
    public Result getConsigneeByName(@PathVariable String consigneeName) {
        return consigneeService.getConsigneeByName(consigneeName);
    }


    @ApiOperation("添加新收货人")
    @PostMapping("/add")
    public Result addConsignee(@RequestBody ConsigneeDto consigneeDto) {
        Consignee consignee = Convert.convert(Consignee.class, consigneeDto);
        Result r = consigneeService.addConsignee(consignee);
        return r;
    }

    @ApiOperation("修改收货人")
    @PutMapping("/update")
    public Result update(@RequestBody ConsigneeDto consigneeDto) {
        Consignee consignee = Convert.convert(Consignee.class, consigneeDto);
        return consigneeService.updateConsignee(consignee);
    }

    @ApiOperation("删除收货人")
    @DeleteMapping("/delete/{consigneeId}")
    public Result deleteConsignee(@PathVariable String consigneeId) {
        return  consigneeService.deleteConsignee(consigneeId);
    }

}


