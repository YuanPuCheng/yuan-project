package com.zihui.cwoa.processone.tasklistener;

import com.zihui.cwoa.processone.service.FinancialTaskService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AskForProMoneyTaskListener implements TaskListener {

    /**
     *  项目请款流程结束的监听任务，把数据存入数据库
     */
    public void notify(DelegateTask delegateTask) {
        String userId = (String) delegateTask.getVariable("userId");
        String processInstanceId = delegateTask.getProcessInstanceId();
        String userProject = (String) delegateTask.getVariable("userProject");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        String flowMoneyOut=null;
        String flowType=null;
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if("请款类型".equals(indexName)){
                flowType= (String) map.get("indexValue");
            }
            if("请款金额".equals(indexName)){
                flowMoneyOut= (String) map.get("indexValue");
            }
        }
        FinancialTaskService financialTaskService= (FinancialTaskService) SpringUtil.getObject("finMoneyFlow");
        financialTaskService.insertProMoneyFlowOut(userId, userProject, flowMoneyOut, processInstanceId,flowType);
    }
}
