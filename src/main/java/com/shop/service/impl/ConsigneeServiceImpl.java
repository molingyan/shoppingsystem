package com.shop.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.R.Result;
import com.shop.dao.ConsigneeMapper;
import com.shop.domain.dto.ConsigneeDto;
import com.shop.domain.pojo.Consignee;
import com.shop.service.ConsigneeService;
import com.shop.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsigneeServiceImpl extends ServiceImpl<ConsigneeMapper, Consignee> implements ConsigneeService {
    @Autowired
    private ConsigneeMapper consigneeMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Result getConsigneeById(String consigneeId) {
        Consignee consignee = consigneeMapper.getConsigneeById(consigneeId);
        if (consignee == null)
            return Result.error("未查到数据");
        ConsigneeDto consigneeDto = Convert.convert(ConsigneeDto.class, consignee);
        return Result.success(consigneeDto);
    }


    @Override
    public Result addConsignee(Consignee consignee) {
        Date date = Convert.toDate(System.currentTimeMillis());
        consignee.setConsigneeId(IdUtils.getConsigneeId(redisTemplate));
        consignee.setUpdateDate(date);
        consignee.setCreateDate(date);
        if (consigneeMapper.addConsignee(consignee) > 0)
            return Result.success("添加成功");
        else
            return Result.error("添加失败");
    }


    @Override
    public Result getConsigneesByUserId(String userId) {
        List<Consignee> consigneeList = consigneeMapper.getConsigneesByUserId(userId);
        if (consigneeList == null)
        return Result.error("未查到数据");
        List<ConsigneeDto> consigneeDtos = Convert.toList(ConsigneeDto.class, consigneeList);
        return Result.success(consigneeDtos);
    }


    @Override
    public Result deleteConsignee(String consigneeId) {
        int i = consigneeMapper.deleteConsignee(consigneeId);
        if (i <= 0) return Result.success("删除失败");
        return Result.success("删除成功");
    }

    @Override
    public Result updateConsignee(Consignee consignee) {
        Date date = Convert.toDate(System.currentTimeMillis());
        consignee.setUpdateDate(date);
        if (consigneeMapper.updateConsignee(consignee) <= 0)
            return Result.success("修改失败");
        return Result.success("修改成功");
    }

    @Override
    public Result getConsigneeByName(String phone) {
        List<Consignee> consignees= consigneeMapper.getConsigneeByPhone(phone);
        if (consignees == null)
            return Result.error("未查到数据");
        List<ConsigneeDto> consigneeDtos = Convert.toList(ConsigneeDto.class, consignees);
        return Result.success(consigneeDtos);
    }




}

