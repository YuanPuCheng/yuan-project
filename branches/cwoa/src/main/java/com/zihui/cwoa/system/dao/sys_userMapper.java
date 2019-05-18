package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.pojo.sys_users;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface sys_userMapper {
    int deleteByPrimaryKey(Integer userId);

    int insertSelective(sys_user record);

    sys_user selectByPrimaryKey(sys_user key);

    int updateByPrimaryKeySelective(sys_user record);

    sys_user selectUserByCode(String userCode);

    List<sys_users> userRoleQuery(@Param("roleId")Integer roleId, @Param("projectId")Integer projectId);

    sys_user selectUserInfo(Integer userId);

    List<sys_user> selectUserByPage(@Param("user")sys_user user,@Param("page")Integer page,@Param("limit")Integer limit);

    Integer selectUserByPageCount(sys_user user);

    List<sys_users> selectUserAndProject();

}