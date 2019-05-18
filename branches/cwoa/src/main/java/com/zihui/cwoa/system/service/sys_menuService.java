package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_menuMapper;
import com.zihui.cwoa.system.pojo.sys_menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_menuService {

    @Resource
    private sys_menuMapper menuMapper;



    public int deleteByPrimaryKey(Integer menuId){
        return menuMapper.deleteByPrimaryKey(menuId);
    };


    public int insertSelective(sys_menu record){
        return menuMapper.insertSelective(record);
    };

    public sys_menu selectByPrimaryKey(Integer menuId){
        return menuMapper.selectByPrimaryKey(menuId);
    };

    public int updateByPrimaryKeySelective(sys_menu record){
        return menuMapper.updateByPrimaryKeySelective(record);
    };

    public List<sys_menu> selectMenuByRoleId(List<Integer> roleId){
        return menuMapper.selectMenuByRoleId(roleId);
    };

    public List<sys_menu> selectMenuList(sys_menu menu){
        return menuMapper.selectMenuList(menu);
    };

    public List<Integer>selectMenuIdByroleId(Integer roleId){
        return menuMapper.selectMenuIdByroleId(roleId);
    };

}
