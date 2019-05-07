package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_task_bMapper;
import com.zihui.cwoa.system.pojo.sys_task_b;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_task_bService {

    @Resource
    private sys_task_bMapper task_bMapper;

    public int deleteByPrimaryKey(Integer taskBId){
        return task_bMapper.deleteByPrimaryKey(taskBId);
    };


    public int insertSelective(sys_task_b record){
        return task_bMapper.insertSelective(record);
    };

    public sys_task_b selectByPrimaryKey(Integer taskBId){
        return task_bMapper.selectByPrimaryKey(taskBId);
    };

    public int updateByPrimaryKeySelective(sys_task_b record){
        return task_bMapper.updateByPrimaryKeySelective(record);
    };


    public List<sys_task_b> taskbQueryw(Integer taskId){
        return task_bMapper.taskbQuery(taskId);
    };

    public int updateByTaskId(Integer taskId,Integer taskStatus){
        return task_bMapper.updateByTaskId(taskId,taskStatus);
    };

    public int deleteByTaskId(Integer taskId){
        return task_bMapper.deleteByTaskId(taskId);
    };
}
