package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sys_roleMapper {
    int deleteByPrimaryKey(Integer roleId);


    int insertSelective(sys_role record);

    sys_role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(sys_role record);


    List<sys_role> selcetRoleByUserId(Integer userId);

    List<sys_role> selectRolebySelect(@Param("roleId")Integer roleId);

    List<sys_role> selectRoleByPage(@Param("page")Integer page,@Param("limit") Integer limit);

    Integer selectRoleByPageCount();

    List<sys_role> selectRoleToUser(@Param("userId")Integer userId);
}