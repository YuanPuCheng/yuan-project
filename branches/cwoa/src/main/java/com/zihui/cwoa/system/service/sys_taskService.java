package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.dao.sys_taskMapper;
import com.zihui.cwoa.system.pojo.sys_task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class sys_taskService {


    @Resource
    private sys_taskMapper taskMapper;

    public int deleteByPrimaryKey(Integer taskId){

        return taskMapper.deleteByPrimaryKey(taskId);
    };


    public int insertSelective(sys_task record){
        return taskMapper.insertSelective(record);
    };

    public sys_task selectByPrimaryKey(Integer taskId){
        return taskMapper.selectByPrimaryKey(taskId);
    };

    public int updateByPrimaryKeySelective(sys_task record){
        return  taskMapper.updateByPrimaryKeySelective(record);
    };

    public List<sys_task> selectZhiPaiTask(Integer userId,Integer page,Integer limit){
        return taskMapper.selectZhiPaiTask(userId,page,limit);
    };

    public Integer selectZhiPaiTaskCount(Integer userId){
        return taskMapper.selectZhiPaiTaskCount(userId);
    };
}
