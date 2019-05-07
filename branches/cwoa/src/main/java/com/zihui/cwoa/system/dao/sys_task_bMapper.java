package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_task_b;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface sys_task_bMapper {
    int deleteByPrimaryKey(Integer taskBId);


    int insertSelective(sys_task_b record);

    sys_task_b selectByPrimaryKey(Integer taskBId);

    int updateByPrimaryKeySelective(sys_task_b record);

    List<sys_task_b> taskbQuery(@Param("taskId")Integer taskId);

    int updateByTaskId(@Param("taskId")Integer taskId,@Param("taskStatus")Integer taskStatus);

    int deleteByTaskId(@Param("taskId")Integer taskId);

}