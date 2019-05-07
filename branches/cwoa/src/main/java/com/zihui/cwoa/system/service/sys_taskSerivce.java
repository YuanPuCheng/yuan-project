package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_taskMapper;
import com.zihui.cwoa.system.pojo.sys_task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_taskSerivce {

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
        return taskMapper.updateByPrimaryKeySelective(record);
    };

    public List<sys_task> selectTaskByPage(Integer userId, Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return taskMapper.selectTaskByPage(userId,page,limit);
    };

    public Integer selectTaskByPageCount(Integer userId){
        return taskMapper.selectTaskByPageCount(userId);
    };

    public List<sys_task> myTaskbyQuery(Integer userId,Integer page,Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return taskMapper.myTaskbyQuery(userId,page,limit);
    };
    public Integer myTaskbyQueryCount(Integer userId){
        return taskMapper.myTaskbyQueryCount(userId);
    };
}
