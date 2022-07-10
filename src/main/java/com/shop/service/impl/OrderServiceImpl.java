package com.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.R.Result;
import com.shop.dao.*;
import com.shop.domain.dto.ConsigneeDto;
import com.shop.domain.dto.OrderDto;
import com.shop.domain.dto.OrderGoodsCount;
import com.shop.domain.dto.UserDto;
import com.shop.domain.pojo.*;
import com.shop.service.OrderService;
import com.shop.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConsigneeMapper consigneeMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final ObjectMapper redisJsonMapper = new ObjectMapper();
    @Override
    public Result getOrderById(String orderId) {
        User user;
        Order order = orderMapper.getOrderById(orderId);
        if (order == null)
            return Result.error("未查到数据");
        OrderDto orderDto = Convert.convert(OrderDto.class, order);
        String userId = order.getUserId();
        try {
            String s = redisTemplate.opsForValue().get("user:userId:user:" + userId);
            if (s == null) {
                user = userMapper.getUserById(userId);
                String userStr = redisJsonMapper.writeValueAsString(user);
                redisTemplate.opsForValue().set("user:userId:user:" + userId, userStr, 600, TimeUnit.MINUTES);
            } else {
                user = redisJsonMapper.readValue(s, User.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        UserDto userDto = Convert.convert(UserDto.class, user);
        orderDto.setUser(userDto);
        Consignee consignee = consigneeMapper.getConsigneeById(order.getConsigneeId());
        ConsigneeDto consigneeDto = Convert.convert(ConsigneeDto.class, consignee);
        orderDto.setConsignee(consigneeDto);
        List<OrderGoodsCount> goodsCounts = orderGoodsMapper.getGoodsCount(orderId);
        int money = 0;
        for (int i = 0; i < goodsCounts.size(); i++) {
            try {
                Goods goods;
                String goodsId = goodsCounts.get(i).getGoodsId();
                String goodsStr = redisTemplate.opsForValue().get("goods:goodId:goods:" + goodsId);
                if (goodsStr != null) {
                    goods = redisJsonMapper.readValue(goodsStr, Goods.class);
                } else {
                    goods = goodsMapper.getGoodsById(goodsId);
                    String s = redisJsonMapper.writeValueAsString(goods);
                    redisTemplate.opsForValue().set("goods:goodId:goods:" + goodsId,s,600, TimeUnit.MINUTES);
                }
                goodsCounts.get(i).setGoodsName(goods.getGoodsName());
                money += goods.getGoodsPrice() * goodsCounts.get(i).getAmount();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
        orderDto.setSumMoney(money);
        orderDto.setGoodsList(goodsCounts);
        return Result.success(orderDto);
    }
    @Override
    @Transactional
    public Result addOrder(OrderAdd orderAddBean) {
        OrderDto orderDto = new OrderDto();
        Date date = Convert.toDate(System.currentTimeMillis());
        orderDto.setOrderId(IdUtils.getOrderId());
        orderDto.setUpdateTime(date);
        orderDto.setOrderTime(date);
        UserDto user = new UserDto();
        user.setUserId(orderAddBean.getUserId());
        orderDto.setUser(user);
        ConsigneeDto consigneeDto = new ConsigneeDto();
        consigneeDto.setConsigneeId(orderAddBean.getConsigneeId());
        orderDto.setConsignee(consigneeDto);
        orderDto.setGoodsList(orderAddBean.getGoodsList());
        Order order = Convert.convert(Order.class, orderDto);
        order.setUserId(orderDto.getUser().getUserId());
        order.setConsigneeId(orderDto.getConsignee().getConsigneeId());
        List<OrderGoodsCount> goodsList = orderDto.getGoodsList();
        int in = orderMapper.addOrder(order);
        for (int i = 0; i < goodsList.size(); i++) {
            GoodsOrder goodsOrder = new GoodsOrder(order.getOrderId(), goodsList.get(i).getGoodsId(), goodsList.get(i).getAmount());
            orderGoodsMapper.addOrderGoods(goodsOrder);
        }
        if (in > 0) {
            return Result.success("添加成功");
        } else
            return Result.error("添加失败");
    }
    @Override
    public Result getOrderList() {
        List<Order> orderList = orderMapper.getOrderList();
        List<OrderDto> orderDtos = Convert.toList(OrderDto.class, orderList);
        for (int i = 0; i < orderDtos.size(); i++) {
            User user = userMapper.getUserById(orderList.get(i).getUserId());
            UserDto userDto = Convert.convert(UserDto.class, user);
            orderDtos.get(i).setUser(userDto);
            Consignee consignee = consigneeMapper.getConsigneeById(orderList.get(i).getConsigneeId());
            ConsigneeDto consigneeDto = Convert.convert(ConsigneeDto.class, consignee);
            orderDtos.get(i).setConsignee(consigneeDto);
            List<OrderGoodsCount> goodsCounts = orderGoodsMapper.getGoodsCount(orderList.get(i).getOrderId());
            int money = 0;
            for (int j = 0; j < goodsCounts.size(); j++) {
                String goodsId = goodsCounts.get(j).getGoodsId();
                Goods goods = goodsMapper.getGoodsById(goodsId);
                goodsCounts.get(j).setGoodsName(goods.getGoodsName());
                money += goods.getGoodsPrice() * goodsCounts.get(j).getAmount();
            }
            orderDtos.get(i).setSumMoney(money);
            orderDtos.get(i).setGoodsList(goodsCounts);
        }
        System.out.println(orderDtos);
        return Result.success(orderDtos);
    }
/*
    @Override
    public Result deleteOrder(String orderId) {
        int i = orderMapper.deleteOrder(orderId);
        if (i <= 0) return Result.success("删除失败");
        return Result.success("删除成功");
    }*/
    @Override
    public Result getOrderListByUserId(String userId) {
        List<Order> orders = orderMapper.getOrderListByUserId(userId);
        if (orders == null)
            return Result.error("未查到数据");
        List<OrderDto> orderDtos = Convert.toList(OrderDto.class, orders);
        return Result.success(orderDtos);
    }
    @Override
    public Result getOrderListByGoodsId(String goodsId) {
        List<Order> orders = orderMapper.getOrderListByGoodsId(goodsId);
        if (orders == null)
            return Result.error("未查到数据");
        List<OrderDto> orderDtos = Convert.toList(OrderDto.class, orders);
        return Result.success(orderDtos);
    }
    @Override
    public Result getOrderListByConId(String consigneeId) {
        List<Order> orders = orderMapper.getOrderListByConId(consigneeId);
        if (orders == null)
            return Result.error("未查到数据");
        List<OrderDto> orderDtos = Convert.toList(OrderDto.class, orders);
        return Result.success(orderDtos);
    }
}

