package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface sys_departmentMapper {
    int deleteByPrimaryKey(Integer departmentId);

    int insertSelective(sys_department record);

    sys_department selectByPrimaryKey(Integer departmentId);

    int updateByPrimaryKeySelective(sys_department record);

    List<sys_department> selectDepartmentById(List<Integer> departmentId);

    List<sys_department> selectDepartment(Integer userId);

    List<sys_department> selectMenu(List<Integer> departmentId);

    List<sys_department> selectDpeartmentListPage(Map map);
    List<sys_department> selectDpeartmentList(sys_department record);


   Integer selectDpeartmentCount(@Param("departmentName") String departmentName);

}