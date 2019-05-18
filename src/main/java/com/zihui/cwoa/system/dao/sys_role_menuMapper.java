package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_role_menu;
import org.apache.ibatis.annotations.Param;

public interface sys_role_menuMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(sys_role_menu record);

    sys_role_menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(sys_role_menu record);

    int insertRoleAndMenu(@Param("roleId")Integer roleId,@Param("menuId")Integer menuId);

    int deleteRoleAndMenu(@Param("roleId")Integer roleId,@Param("menuId")Integer menuId);

    int deleteMenuByRoleId(@Param("roleId")Integer roleId);

}