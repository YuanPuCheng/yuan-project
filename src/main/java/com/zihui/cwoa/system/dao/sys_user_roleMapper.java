package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_user_role;
import org.apache.ibatis.annotations.Param;

public interface sys_user_roleMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(sys_user_role record);

    sys_user_role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(sys_user_role record);

    int insertUserAndRole(@Param("userId")Integer userId,@Param("roleId")Integer roleId);

    int deleteUserRole(@Param("userId")Integer userId,@Param("roleId")Integer roleId);



}