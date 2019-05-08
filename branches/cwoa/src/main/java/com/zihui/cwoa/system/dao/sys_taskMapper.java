package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sys_taskMapper {
    int deleteByPrimaryKey(Integer taskId);


    int insertSelective(sys_task record);

    sys_task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(sys_task record);

    List<sys_task> selectTaskByPage(@Param("userId")Integer userId,@Param("page")Integer page,@Param("limit")Integer limit);

    Integer selectTaskByPageCount(@Param("userId")Integer userId);

    List<sys_task> myTaskbyQuery(@Param("userId")Integer userId,@Param("page")Integer page,@Param("limit")Integer limit);

    Integer myTaskbyQueryCount(@Param("userId")Integer userId);

    Integer myTaskCount(@Param("userId")Integer userId);

}