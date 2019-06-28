package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.FinancialTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finMoneyFlow")
public class FinancialTaskService {

    @Autowired
    private FinancialTaskMapper financialTaskMapper;

    public void insertMoneyFlowOut(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
       financialTaskMapper.insertMoneyFlowOut(userId, userProject, flowMoneyOut, processInstanceId,flowType);
    }

    public void insertMoneyFlowIn(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        financialTaskMapper.insertMoneyFlowIn(userId, userProject, flowMoneyOut, processInstanceId,flowType);
    }
    public void insertProMoneyFlowIn(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        financialTaskMapper.insertProMoneyFlowIn(userId, userProject, flowMoneyOut, processInstanceId,flowType);
    }

    public void insertProMoneyFlowOut(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        financialTaskMapper.insertProMoneyFlowOut(userId, userProject, flowMoneyOut, processInstanceId,flowType);
    }

}
