package com.zihui.cwoa.processone.config;

import com.zihui.cwoa.processone.service.FinancialTaskService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AskForReimburseTaskListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        String userCode = (String) delegateTask.getVariable("userCode");
        String userProject = (String) delegateTask.getVariable("userProject");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        String flowMoneyIn=null;
        String flowType=null;
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if(indexName.equals("报销金额")){
                flowMoneyIn= (String) map.get("indexValue");
            }
            if(indexName.equals("报销类型")){
                flowType= (String) map.get("indexValue");
            }
        }
        FinancialTaskService financialTaskService= (FinancialTaskService) SpringUtil.getObject("finMoneyFlow");
        financialTaskService.insertMoneyFlowIn(userCode, userProject, flowMoneyIn, processInstanceId,flowType);
    }
}
