package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sys_departmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insertSelective(sys_department record);

    sys_department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(sys_department record);

    List<sys_department> selectByListSelect();

    List<sys_department> selectDeperByPage(@Param("departmentName")String departmentName,@Param("page")Integer page
    ,@Param("limit")Integer limit);

    Integer selectDeperByPageCount(@Param("departmentName")String departmentName);
}