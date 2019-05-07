package com.zihui.cwoa.processone.tasklistener;

import com.zihui.cwoa.processone.service.LeaveTaskService;
import com.zihui.cwoa.system.config.SpringUtil;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class AskForBusinessTaskListener implements TaskListener {

    public void notify(DelegateTask delegateTask) {
        List<String> list=new ArrayList<>();
        String userCode = (String) delegateTask.getVariable("userCode");
        String userProject = (String) delegateTask.getVariable("userProject");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        int leavedays=(int) delegateTask.getVariable("leavedays");
        String leaveTime="";
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if(indexName.equals("出差时间")){
                leaveTime= (String) map.get("indexValue");

            }
        }
        String startTime = leaveTime.substring(0, 10);
        list.add("('"+userCode+"','"+userProject+"',YEAR('"+startTime+"'),MONTH('"+startTime+"'),'"+startTime+"','出差')");
        if (leavedays>1){
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = sdt.parse(startTime);
                long parseTime = parse.getTime();
                for (int i=1;i<leavedays;i++){
                    Date date=new Date(parseTime+86400000*i);
                    String format = sdt.format(date);
                    list.add("('"+userCode+"','"+userProject+"',YEAR('"+format+"'),MONTH('"+format+"'),'"+format+"','出差')");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        LeaveTaskService leaveTaskService= (LeaveTaskService) SpringUtil.getObject("leaveTask");
        leaveTaskService.insertLeave(list);
    }
}
