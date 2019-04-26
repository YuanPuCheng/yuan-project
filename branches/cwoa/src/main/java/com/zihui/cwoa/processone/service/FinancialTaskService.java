package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.FinancialTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finMoneyFlow")
public class FinancialTaskService {

    @Autowired
    private FinancialTaskMapper financialTaskMapper;

    public Integer insertMoneyFlowOut(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        return financialTaskMapper.insertMoneyFlowOut(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }

    public Integer insertMoneyFlowIn(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        return financialTaskMapper.insertMoneyFlowIn(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }
}
