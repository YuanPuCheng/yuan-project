package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.FinancialTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finMoneyFlow")
public class FinancialTaskService {

    @Autowired
    private FinancialTaskMapper financialTaskMapper;

    public void insertMoneyFlowOut(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
       financialTaskMapper.insertMoneyFlowOut(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }

    public void insertMoneyFlowIn(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        financialTaskMapper.insertMoneyFlowIn(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }
    public void insertProMoneyFlowIn(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        financialTaskMapper.insertProMoneyFlowIn(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }

    public void insertProMoneyFlowOut(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType){
        financialTaskMapper.insertProMoneyFlowOut(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }

}
