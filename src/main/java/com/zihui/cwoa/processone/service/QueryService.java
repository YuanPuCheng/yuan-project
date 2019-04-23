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
    public List<String> queryCodeByName(String userName){
        return queryMapper.queryCodeByName(userName);
    }
}
