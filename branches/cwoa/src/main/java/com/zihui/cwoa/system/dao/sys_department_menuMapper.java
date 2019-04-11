package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_department_menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface sys_department_menuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sys_department_menu record);

    sys_department_menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(sys_department_menu record);

    List<Integer> selectMenuIdByUserId(Integer userId);
}