package com.shop.controller;

import cn.hutool.core.convert.Convert;
import com.shop.R.Result;
import com.shop.domain.dto.OrderDto;
import com.shop.domain.pojo.Order;
import com.shop.domain.pojo.OrderAdd;
import com.shop.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags={"订单接口"})
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    @ApiOperation("根据订单ID查询订单")
    @GetMapping("/orderId/{orderId}")
    public Result getOrderById(@PathVariable String orderId) {
        Result r = orderService.getOrderById(orderId);
        return r;
    }


    @ApiOperation("查询订单列表")
    @GetMapping("/orderList")
    public Result getOrderList() {
        Result r = orderService.getOrderList();
        return r;
    }

    @ApiOperation("添加新订单")
    @PostMapping("/add")
    public Result addOrder(@RequestBody OrderAdd orderAddBean) {
        Result r = orderService.addOrder(orderAddBean);
        return r;
    }


/*    @ApiOperation("删除订单")
    @DeleteMapping("/delete/{orderId}")
    public Result deleteOrder(@PathVariable String orderId) {
        return  orderService.deleteOrder(orderId);
    }*/




    @ApiOperation("根据收货人ID查询订单")
    @GetMapping("/cid/{conId}")
    public Result getOrderByConId(@PathVariable String conId) {
        return orderService.getOrderListByUserId(conId);
    }

    @ApiOperation("根据商品ID查询订单")
    @GetMapping("/goodsId/{goodsId}")
    public Result getOrderByGoodsId(@PathVariable String goodsId) {
        return orderService.getOrderListByUserId(goodsId);
    }
}


