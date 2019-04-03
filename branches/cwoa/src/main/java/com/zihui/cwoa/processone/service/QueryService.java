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
     *  @return 查询结果
     */
    public List<String> queryProNotActiveByUserCode(String userCode){
        return queryMapper.queryProNotActiveByUserCode(userCode);
    }

    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    public String queryProStatuByProInstanceId(String processInstanceId){
        return queryMapper.queryProStatuByProInstanceId(processInstanceId);
    }
}
