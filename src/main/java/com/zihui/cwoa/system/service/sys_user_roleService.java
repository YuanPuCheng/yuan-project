package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.dao.sys_user_roleMapper;
import com.zihui.cwoa.system.pojo.sys_user_role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class sys_user_roleService {

    @Resource
    private sys_user_roleMapper userRoleMapper;


    public int deleteByPrimaryKey(Integer id){
        return userRoleMapper.deleteByPrimaryKey(id);
    };


    public int insertSelective(sys_user_role record){
        return userRoleMapper.insertSelective(record);
    };

    public sys_user_role selectByPrimaryKey(Integer id){
        return userRoleMapper.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(sys_user_role record){
        return userRoleMapper.updateByPrimaryKeySelective(record);
    };

    public int insertUserAndRole(Integer userId,Integer roleId){
        return userRoleMapper.insertUserAndRole(userId,roleId);
    };

    public int deleteUserRole(Integer userId,Integer roleId){
        return userRoleMapper.deleteUserRole(userId,roleId);
    };
}
