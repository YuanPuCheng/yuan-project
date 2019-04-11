package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.pojo.sys_user;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface sys_userMapper {



    int deleteByPrimaryKey(Integer userId);

    int insertSelective(sys_user record);

    sys_user selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(sys_user record);

    sys_user selectUserByLogin(String usercode);

    sys_user selectDepartmentToUser(Integer userId);

    List<sys_user> selectUserList(sys_user record);

}