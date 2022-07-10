package com.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.R.Result;
import com.shop.domain.pojo.Goods;

public interface GoodsService extends IService<Goods> {
    public Result getGoodsById(String goodsId);

    public Result addGoods(Goods goods);

    public Result getGoodsList();

    public Result deleteGoods(String goodsId);
    public Result updateGoods(Goods goods);
    public Result getGoodsByName(String name);
}

