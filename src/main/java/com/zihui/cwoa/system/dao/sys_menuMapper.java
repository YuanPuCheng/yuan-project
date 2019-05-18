package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sys_menuMapper {
    int deleteByPrimaryKey(Integer menuId);


    int insertSelective(sys_menu record);

    sys_menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(sys_menu record);

    List<sys_menu> selectMenuByRoleId(List<Integer> roleId);

    List<sys_menu> selectMenuList(sys_menu menu);

    List<Integer>selectMenuIdByroleId(@Param("roleId")Integer roleId);

}