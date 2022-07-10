package com.shop.service;

import java.util.List;
import java.util.Set;

public interface UserRoleService {
    public List<String> getUserRoleByUserName(String username);

    public  List<String> getUserRoleById(String uid);

}
