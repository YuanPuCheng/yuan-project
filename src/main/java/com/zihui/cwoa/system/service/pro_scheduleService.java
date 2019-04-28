package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.pro_scheduleMapper;
import com.zihui.cwoa.system.pojo.pro_schedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class pro_scheduleService {

    @Resource
    private pro_scheduleMapper scheduleMapper;

     public int deleteByPrimaryKey(Integer scheduleId){
        return scheduleMapper.deleteByPrimaryKey(scheduleId);
    };


    public int insertSelective(pro_schedule record){
        return scheduleMapper.insertSelective(record);
    };

    public pro_schedule selectByPrimaryKey(Integer scheduleId){
        return scheduleMapper.selectByPrimaryKey(scheduleId);
    };

    public int updateByPrimaryKeySelective(pro_schedule record){
        return scheduleMapper.updateByPrimaryKeySelective(record);
    };

     public int deleteByProjectId(@Param("projectId") Integer projectId){
        return scheduleMapper.deleteByProjectId(projectId);
    };
}
