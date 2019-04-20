package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_user_department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface sys_user_departmentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(sys_user_department record);

    sys_user_department selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(sys_user_department record);

    List<String> selectDepartmentByUserId(Integer userId);

    int insertUserDepar(@Param("userId") Integer userId, @Param("departmentId")Integer departmentId );

    int updateUserDepar(@Param("userId")Integer userId, @Param("departmentId")Integer departmentId );

    int deleteUserDepar(@Param("userId")Integer userId, @Param("departmentId")Integer departmentId );

}