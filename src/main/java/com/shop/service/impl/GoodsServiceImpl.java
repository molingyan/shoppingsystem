package com.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.R.Result;
import com.shop.dao.GoodsMapper;
import com.shop.domain.dto.GoodsDto;
import com.shop.domain.pojo.Goods;
import com.shop.service.GoodsService;
import com.shop.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final ObjectMapper redisJsonMapper = new ObjectMapper();


    @Override
    public Result getGoodsById(String goodsId) {
        String goodsStr;
        Goods goods;
        try {
            goodsStr = redisTemplate.opsForValue().get("goods:goodsId:goods:" + goodsId);
            if (goodsStr != null) {
               goods = redisJsonMapper.readValue(goodsStr, Goods.class);
            } else {
                goods = goodsMapper.getGoodsById(goodsId);
                goodsStr = redisJsonMapper.writeValueAsString(goods);
                redisTemplate.opsForValue().set("goods:goodsId:goods:" + goodsId,goodsStr,600, TimeUnit.MINUTES);
            }
            if (goods == null)
                return Result.error("未查到数据");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        GoodsDto goodsDto = Convert.convert(GoodsDto.class, goods);
        return Result.success(goodsDto);
    }


    @Override
    public Result addGoods(Goods goods) {
        Date date = Convert.toDate(System.currentTimeMillis());
        goods.setGoodsId(IdUtils.getGoodsId(redisTemplate));
        goods.setUpdateDate(date);
        goods.setCreateDate(date);
        if (goodsMapper.addGoods(goods) > 0)
            return Result.success("添加成功");
        else
            return Result.error("添加失败");
    }

    @Override
    public Result getGoodsList() {
        List<Goods> goodsList = goodsMapper.getGoodsList();
        System.out.println(goodsList);
        List<GoodsDto> goodsDtos = Convert.toList(GoodsDto.class, goodsList);
        return Result.success(goodsDtos);
    }


    @Override
    public Result deleteGoods(String goodsId) {
        int i = goodsMapper.deleteGoods(goodsId);
        redisTemplate.delete("goods:goodsId:goods:"+goodsId);
        if (i <= 0) return Result.success("删除失败");
        return Result.success("删除成功");
    }

    @Override
    public Result updateGoods(Goods goods) {
        Date date = Convert.toDate(System.currentTimeMillis());
        goods.setUpdateDate(date);
        redisTemplate.delete("goods:goodsId:goods:"+goods.getGoodsId());
        if (goodsMapper.updateGoods(goods) <= 0)
            return Result.success("修改失败");
        return Result.success("修改成功");
    }

    @Override
    public Result getGoodsByName(String name) {
        Goods goods = goodsMapper.getGoodsByName(name);
        if (goods == null)
            return Result.error("未查到数据");
        GoodsDto goodsDto = Convert.convert(GoodsDto.class, goods);
        return Result.success(goodsDto);
    }


}

