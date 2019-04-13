package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface sys_departmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insertSelective(sys_department record);

    sys_department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(sys_department record);

    List<sys_department> selectDepartmentById(List<Integer> departmentId);

    List<sys_department> selectDepartment(Integer userId);

    List<sys_department> selectMenu(List<Integer> departmentId);

    List<sys_department> selectDpeartmentList(sys_department record);

}