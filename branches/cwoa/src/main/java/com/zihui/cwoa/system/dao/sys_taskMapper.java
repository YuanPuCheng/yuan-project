package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface sys_taskMapper {

    int deleteByPrimaryKey(Integer taskId);


    int insertSelective(sys_task record);

    sys_task selectByPrimaryKey(Integer taskId);

    int updateByPrimaryKeySelective(sys_task record);

    List<sys_task> selectZhiPaiTask(@Param("userId")Integer userId,@Param("page")Integer page,@Param("limit")Integer limit);

    Integer selectZhiPaiTaskCount(@Param("userId")Integer userId);

}