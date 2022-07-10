package com.shop.service;

import com.shop.R.Result;
import com.shop.domain.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginService {

    public Result login(@RequestBody User user);
    public Result logout();
}
