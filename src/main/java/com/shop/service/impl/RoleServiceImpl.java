package com.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.dao.RoleMapper;
import com.shop.domain.pojo.Role;
import com.shop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;


  /*  @Override
    public R getRoleById(String uId) {
        Role role = roleMapper.getRoleById(uId);
        if (role!=null){
            return new R(role,true);
        }
        else return new R(false,"查不到");
    }

    @Override
    public R addRole(Role role) {
        Boolean flag = roleMapper.addRole(role)>0;
        return new R("",flag);
    }

    @Override
    public R getRoleList() {
        List<Role> roleList = roleMapper.getRoleList();
        return new R<>(roleList,true);
    }

    @Override
    public IPage<Role> getPage(int currentPage, int pageSize, Role role) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<Role>();
        lqw.like(Strings.isNotEmpty(role.getRoleName()),Role::getRoleName,role.getRoleName());
        IPage page = new Page(currentPage,pageSize);
        roleMapper.selectPage(page,lqw);
        return page;
    }


    @Override
    public Boolean deleteRole(String uId) {
        int i = roleMapper.deleteRole(uId);
        return i>0;
    }

    @Override
    public Boolean updateRole(Role role) {

        return  roleMapper.updateRole(role)>0;
    }

    @Override
    public Role getRoleByName(String getRoleByName) {
        Role role = roleMapper.getRoleByName(getRoleByName);
        return role;
    }
*/

}

