package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    private QueryMapper queryMapper;

    /**
     *  根据用户工号查询他发起的未完成的流程的ID
     *  @param userCode 用户工号
     *  @return 流程ID的集合
     */
    public List<String> queryProcessActiveByUserCode(String userCode){
        return queryMapper.queryProcessActiveByUserCode(userCode);
    }

    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    public String queryProStatuByProInstanceId(String processInstanceId){
        return queryMapper.queryProStatuByProInstanceId(processInstanceId);
    }

    /**
     *  根据流程实例ID查询流程名称
     *  @param processInstanceId 流程实例ID
     *  @return 流程名称
     */
    public String queryProNameByProInstanceId(String processInstanceId){
        return queryMapper.queryProNameByProInstanceId(processInstanceId);
    }

    /**
     *  根据用户名查用户工号
     *  @param userName 用户名
     *  @return 用户工号
     */
    public List<String> queryCodeByName(String userName){
        return queryMapper.queryCodeByName(userName);
    }
}
