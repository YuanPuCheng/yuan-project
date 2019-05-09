package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    @Autowired
    private QueryMapper queryMapper;


    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    public String queryProStatuByProInstanceId(String processInstanceId){
        return queryMapper.queryProStatuByProInstanceId(processInstanceId);
    }


    /**
     *  根据用户名查用户工号
     *  @param userName 用户名
     *  @return 用户工号
     */
    public String queryCodeByName(String userName){
        return queryMapper.queryCodeByName(userName);
    }

    /**
     *  根据用户工号查询用户在途流程数
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public Integer queryActProCountByCode(String userCode){
        return queryMapper.queryActProCountByCode(userCode);
    }

    /**
     *  根据用户工号查询用户结束流程数
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public Integer queryEndProCountByCode(String userCode){
        return queryMapper.queryEndProCountByCode(userCode);
    }

    /**
     *  根据用户工号查询用户任务数
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public Integer queryTaskCountByCode(String userCode){
        return queryMapper.queryTaskCountByCode(userCode);
    }

    /**
     *  设置任务办理人
     */
    public void setAssigned(String processInstanceId,String taskName,String userCode){
        queryMapper.setAssigned(processInstanceId,taskName,userCode);
    }

    /**
     *  根据角色名查询它的直接上级角色的ID
     */
    public Integer queryManagerIdByRoleName(String roleName){
        return queryMapper.queryManagerIdByRoleName(roleName);
    }
}
