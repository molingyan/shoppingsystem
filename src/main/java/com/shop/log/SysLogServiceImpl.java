package com.shop.log;

import com.shop.R.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements  SysLogService {
    @Autowired
    private SysMapper sysMapper;
    @Override
    public Result addLog(SysLog log) {
        int i = sysMapper.addSys(log);
        if (i == 0)  return Result.error("添加失败");
        return Result.success("添加成功");
    }
}
