package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = {"QueryServiceCache"})
@Service
public class QueryService {

    @Autowired
    private QueryMapper queryMapper;


    /**
     *  根据流程实例ID查询流程状态
     */
    public String queryProStatusByProInstanceId(String processInstanceId){
        return queryMapper.queryProStatusByProInstanceId(processInstanceId);
    }

    /**
     *  根据用户名查用户ID
     */
    public String queryIdByName(String userName){
        return queryMapper.queryIdByName(userName);
    }

    /**
     *  根据用户ID查询用户在途流程数
     */
    public Integer queryActProCountById(String userId){
        return queryMapper.queryActProCountById(userId);
    }

    /**
     *  根据用户ID查询用户结束流程数
     */
    public Integer queryEndProCountById(String userId){
        return queryMapper.queryEndProCountById(userId);
    }

    /**
     *  根据用户ID查询用户任务数
     */
    public Integer queryTaskCountById(Integer userId){
        return queryMapper.queryTaskCountById(userId);
    }

    /**
     *  设置任务办理人
     */
    public void setAssigned(String processInstanceId,String taskName,String userId){
        queryMapper.setAssigned(processInstanceId,taskName,userId);
    }

    /**
     *  根据角色名查询它的直接上级角色的ID
     */
    public Integer queryManagerIdByRoleName(String roleName){
        return queryMapper.queryManagerIdByRoleName(roleName);
    }

    /**
     *  根据用户ID查询用户审批的流程数
     */
    public Integer queryCheckCountById(String userId){
        return queryMapper.queryCheckCountById(userId);
    }

    /**
     * 根据用户ID查询他审批过的流程
     */
    public List<Map<String,Object>> queryCheckProcessById(String userId,int start,int limit){
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("start",start);
        map.put("limit",limit);
        return queryMapper.queryCheckProcessById(map);
    }

    /**
     * 根据用户ID查询用户名称
     */
    public String selectNameById(String userId){
        return queryMapper.selectNameById(userId);
    }


    @Cacheable(key="'selectProcessSelect'")
    public List<Map<String,Object>> selectProcessSelect(){
        return queryMapper.selectProcessSelect();
    }
}
