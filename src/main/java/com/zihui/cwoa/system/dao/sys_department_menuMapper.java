package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_department_menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface sys_department_menuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("departmentId")Integer departmentId,@Param("menuId")Integer menuId);

    sys_department_menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(sys_department_menu record);

    List<Integer> selectMenuIdByUserId(Integer userId);

    List<Integer> selectMenuIdByDeparId(@Param("departmentId") Integer departmentId);

    int deleteByDeparIdAndMenuId(@Param("departmentId") Integer departmentId,@Param("menuId") Integer menuId);
}