package com.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.R.Result;
import com.shop.domain.pojo.Type;

public interface TypeService extends IService<Type> {
    public Result getTypeById(String typeId);

    public Result addType(Type type);

    public Result getTypeList();

    public Result deleteType(String typeId);
    public Result updateType(Type type);
    public Result getTypeByName(String name);
}

