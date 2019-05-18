package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_departmentMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class sys_departmentService {

    @Resource
    private sys_departmentMapper departmentMapper;


    public int deleteByPrimaryKey(Integer departmentId){
        return departmentMapper.deleteByPrimaryKey(departmentId);
    };

    public int insertSelective(sys_department record){
        return departmentMapper.insertSelective(record);
    };

    public sys_department selectByPrimaryKey(Integer departmentId){
        return departmentMapper.selectByPrimaryKey(departmentId);
    };

    public int updateByPrimaryKeySelective(sys_department record){
        return departmentMapper.updateByPrimaryKeySelective(record);
    };

    public List<sys_department> selectByListSelect(){
        return departmentMapper.selectByListSelect();
    };

    public ConcurrentMap selectDeperByPage(String departmentName, Integer page, Integer limit){
        ConcurrentMap concurrentMap = new ConcurrentHashMap();

        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        List<sys_department> list =departmentMapper.selectDeperByPage(departmentName,page,limit);
        Integer count = departmentMapper.selectDeperByPageCount(departmentName);
        concurrentMap.put("count", count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    };

    public Integer selectDeperByPageCount(String departmentName){

        return departmentMapper.selectDeperByPageCount(departmentName);
    };


}
