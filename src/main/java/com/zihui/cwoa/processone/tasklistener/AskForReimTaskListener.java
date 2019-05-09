package com.zihui.cwoa.processone.tasklistener;

import com.zihui.cwoa.processone.service.ProcessesService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

@Component
public class AskForReimTaskListener implements TaskListener {

    /**
     *  如果报销人是项目经理，自动完成审批
     */
    public void notify(DelegateTask delegateTask) {
        String userDepartment = (String) delegateTask.getVariable("userDepartment");
        if ("项目经理".equals(userDepartment)){
            String id = delegateTask.getId();
            ProcessesService processesService= (ProcessesService) SpringUtil.getObject("ProcessesService");
            processesService.completeTask(id);
        }
    }
}
