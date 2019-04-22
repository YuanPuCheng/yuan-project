package com.zihui.cwoa.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zihui.cwoa.system.dao.sys_projectMapper;
import com.zihui.cwoa.system.pojo.sys_project;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_projectService {

    @Resource
    private sys_projectMapper projectMapper;


   public int deleteByPrimaryKey(Integer projectId){
       return projectMapper.deleteByPrimaryKey(projectId);
   };


    public  int insertSelective(sys_project record){
        return projectMapper.insertSelective(record);
    };

    public sys_project selectByPrimaryKey(Integer projectId){
        return projectMapper.selectByPrimaryKey(projectId);
    };

    public int updateByPrimaryKeySelective(sys_project record){
        return projectMapper.updateByPrimaryKeySelective(record);
    };

    /**
     * 查询根据条件查询所有项目
     * @param record
     * @return sys_project 返回所有对象
     */
    public List<sys_project> selectProList(sys_project record,Integer page, Integer limit){
        //PageHelper.startPage(page,limit);
        //PageHelper.startPage(pageNum, pageSize);
        //List list = projectMapper.selectProList(record);
        PageHelper.startPage(page,limit);
        return projectMapper.selectProList(record);
    };
}
