package com.zihui.cwoa.processone.config;

import com.zihui.cwoa.processone.service.FinancialTaskService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AskForProReimburseTaskListener implements TaskListener {

    /**
     *  项目报销流程结束的监听任务，把数据存入数据库
     */
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        String userId = (String) delegateTask.getVariable("userId");
        String userProject = (String) delegateTask.getVariable("userProject");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        String flowMoneyIn=null;
        String flowType=null;
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if("报销类型".equals(indexName)){
                flowType= (String) map.get("indexValue");
            }
            if("报销金额".equals(indexName)){
                flowMoneyIn= (String) map.get("indexValue");
            }
        }
        FinancialTaskService financialTaskService= (FinancialTaskService) SpringUtil.getObject("finMoneyFlow");
        financialTaskService.insertProMoneyFlowIn(userId, userProject, flowMoneyIn, processInstanceId,flowType);
    }
}
