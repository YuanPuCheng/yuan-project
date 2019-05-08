package com.zihui.cwoa.processone.config;

import com.zihui.cwoa.processone.service.FinancialTaskService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AskForMoneyTaskListener implements TaskListener {

    /**
     *  请款流程结束的监听任务，把数据存入数据库
     */
    public void notify(DelegateTask delegateTask) {
        String processInstanceId = delegateTask.getProcessInstanceId();
        String userCode = (String) delegateTask.getVariable("userCode");
        String userProject = (String) delegateTask.getVariable("userProject");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        String flowMoneyOut=null;
        String flowType=null;
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if(indexName.equals("请款金额")){
                flowMoneyOut= (String) map.get("indexValue");
            }
            if(indexName.equals("请款类型")){
                flowType= (String) map.get("indexValue");
            }
        }
        FinancialTaskService financialTaskService= (FinancialTaskService) SpringUtil.getObject("finMoneyFlow");
        financialTaskService.insertMoneyFlowOut(userCode, userProject, flowMoneyOut, processInstanceId,flowType);
    }
}
