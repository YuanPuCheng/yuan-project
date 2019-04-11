package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface sys_menuMapper {

    int deleteByPrimaryKey(Integer menuId);


    int insertSelective(sys_menu record);

    sys_menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(sys_menu record);

    List<sys_menu> selectMenuByMenuId(List<Integer> menuId);
}