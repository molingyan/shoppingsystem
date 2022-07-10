package com.shop.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.R.Result;
import com.shop.domain.dto.GoodsDto;
import com.shop.domain.pojo.Goods;
import com.shop.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags={"商品接口"})
@CrossOrigin
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;



    @ApiOperation("根据商品ID查询商品")
    @GetMapping("/goodsId/{goodsId}")
    public Result getGoodsById(@PathVariable String goodsId) {
        Result r = goodsService.getGoodsById(goodsId);
        return r;
    }

    @ApiOperation("根据商品名查询商品")
    @GetMapping("/name/{goodsName}")
    public Result getGoodsByName(@PathVariable String goodsName) {
        return goodsService.getGoodsByName(goodsName);
    }




    @ApiOperation("添加新商品")
    @PostMapping("/add")
    public Result addGoods(@RequestBody GoodsDto goodsDto) {
        Goods goods = Convert.convert(Goods.class, goodsDto);
        Result r = goodsService.addGoods(goods);
        return r;
    }

    @ApiOperation("修改商品信息")
    @PutMapping("/update")
    public Result update(@RequestBody GoodsDto goodsDto) {
        Goods goods = Convert.convert(Goods.class, goodsDto);
        return goodsService.updateGoods(goods);
    }

    @ApiOperation("删除商品")
    @DeleteMapping("/delete/{goodsId}")
    public Result deleteGoods(@PathVariable String goodsId) {
        return  goodsService.deleteGoods(goodsId);
    }


    @ApiOperation("获取全部商品")
    @GetMapping("/getList")
    public Result getGoodsList() {
        return   goodsService.getGoodsList();
    }
}


