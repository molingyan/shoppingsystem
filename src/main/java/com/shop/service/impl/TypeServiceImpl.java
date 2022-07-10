package com.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.R.Result;
import com.shop.dao.GoodsMapper;
import com.shop.dao.TypeMapper;
import com.shop.domain.dto.TypeDto;
import com.shop.domain.pojo.Goods;
import com.shop.domain.pojo.Type;
import com.shop.service.TypeService;
import com.shop.utils.IdUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    private TypeMapper typeMapper;


    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result getTypeById(String typeId) {
        Type type = typeMapper.getTypeById(typeId);
        if (type == null)
            return Result.error("未查到数据");
        TypeDto typeDto = Convert.convert(TypeDto.class, type);
        List<Type> types = typeMapper.getListByPId(typeDto.getTypeId());
        List<TypeDto> typeDtos = Convert.toList(TypeDto.class, types);
        typeDto.setTypeDtos(typeDtos);
        return Result.success(typeDto);
    }


    @Override
    public Result addType(Type type) {
        Date date = Convert.toDate(System.currentTimeMillis());
        type.setTypeName(IdUtils.getTypeId(stringRedisTemplate));
        type.setUpdateDate(date);
        type.setCreatedDate(date);
        if (typeMapper.addType(type) > 0)
            return Result.success("添加成功");
        else
            return Result.error("添加失败");
    }

    @Override
    public Result getTypeList() {
        List<Type> typeList = typeMapper.getListBylevel(1);
        List<TypeDto> typeDtos = Convert.toList(TypeDto.class, typeList);
        traverseType(typeDtos);
        return Result.success(typeDtos);
    }


    @Transactional
    @Override
    public Result deleteType(String typeId) {
        List<Goods> goodsList = goodsMapper.getGoodsByTypeId(typeId);
        if (goodsList!=null){
            for (Goods goods:goodsList) {
                goodsMapper.updateGoodsType(null,goods.getGoodsId());
            }
        }
        int i = typeMapper.deleteType(typeId);
        if (i <= 0) return Result.success("删除失败");
        return Result.success("删除成功");
    }

    @Override
    public Result updateType(Type type) {
        Date date = Convert.toDate(System.currentTimeMillis());
        type.setUpdateDate(date);
        if (typeMapper.updateType(type) <= 0)
            return Result.success("修改失败");
        return Result.success("修改成功");
    }

    @Override
    public Result getTypeByName(String name) {
        Type type = typeMapper.getTypeByName(name);
        if (type == null)
            return Result.error("未查到数据");
        TypeDto typeDto = Convert.convert(TypeDto.class, type);
        List<Type> types = typeMapper.getListByPId(typeDto.getTypeId());
        List<TypeDto> typeDtos = Convert.toList(TypeDto.class, types);
        typeDto.setTypeDtos(typeDtos);
        return Result.success(typeDto);


    }


    public List<TypeDto> traverseType(List<TypeDto> typeList) {
        List<Type> types;
        for (TypeDto typeDto : typeList) {
            if (typeDto.getTypeLevel() == 3) break;
            types = typeMapper.getListByPId(typeDto.getTypeId());
            List<TypeDto> typeDtos = Convert.toList(TypeDto.class, types);
            typeDto.setTypeDtos(typeDtos);
            traverseType(typeDtos);
        }
        return typeList;
    }

}

