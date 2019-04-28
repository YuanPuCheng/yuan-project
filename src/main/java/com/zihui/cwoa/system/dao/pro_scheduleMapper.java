package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.pro_schedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface pro_scheduleMapper {
    int deleteByPrimaryKey(Integer scheduleId);


    int insertSelective(pro_schedule record);

    pro_schedule selectByPrimaryKey(Integer scheduleId);

    int updateByPrimaryKeySelective(pro_schedule record);

    int deleteByProjectId(@Param("projectId") Integer projectId);

}