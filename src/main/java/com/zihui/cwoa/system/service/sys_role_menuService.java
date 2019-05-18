package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_role_menuMapper;
import com.zihui.cwoa.system.pojo.sys_role_menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class sys_role_menuService {

    @Resource
    private sys_role_menuMapper roleMenuMapper;


    public int deleteByPrimaryKey(Integer id){
        return roleMenuMapper.deleteByPrimaryKey(id);
    };


    public int insertSelective(sys_role_menu record){
        return roleMenuMapper.insertSelective(record);
    };

    public sys_role_menu selectByPrimaryKey(Integer id){
        return roleMenuMapper.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(sys_role_menu record){
        return roleMenuMapper.updateByPrimaryKeySelective(record);
    };


}
