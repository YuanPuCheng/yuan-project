package com.zihui.cwoa.processone.config;

import com.zihui.cwoa.processone.service.FinancialTaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


public class AskForMoneyTaskListener implements TaskListener {

    private FinancialTaskService financialTaskService;

    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        String userCode = (String) delegateTask.getVariable("userCode");
        String userProject = (String) delegateTask.getVariable("userProject");
        String flowMoneyOut = (String) delegateTask.getVariable("flowMoneyOut");
        financialTaskService.insertMoneyFlowOut(userCode,userProject,flowMoneyOut,processInstanceId);
    }
}
