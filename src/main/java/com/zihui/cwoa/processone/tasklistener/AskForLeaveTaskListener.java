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
public class AskForLeaveTaskListener implements TaskListener {

    /**
     *  请假流程结束的监听任务，把数据存入数据库
     */
    public void notify(DelegateTask delegateTask) {
        List<String> list=new ArrayList<>();
        String userId = (String) delegateTask.getVariable("userId");
        String userProject = (String) delegateTask.getVariable("userProject");
        List<Map> processSummary = (List<Map>) delegateTask.getVariable("processSummary");
        int leaveDays=(int) delegateTask.getVariable("leaveDays");
        String leaveTime="";
        for (Map map: processSummary) {
            String indexName= (String) map.get("indexName");
            if("请假时间".equals(indexName)){
                leaveTime= (String) map.get("indexValue");

            }
        }
        String startTime = leaveTime.substring(0, 10);
        list.add("('"+userId+"','"+userProject+"',YEAR('"+startTime+"'),MONTH('"+startTime+"'),'"+startTime+"','请假')");
        if (leaveDays>1){
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = sdt.parse(startTime);
                long parseTime = parse.getTime();
                for (int i=1;i<leaveDays;i++){
                    Date date=new Date(parseTime+86400000*i);
                    String format = sdt.format(date);
                    list.add("('"+userId+"','"+userProject+"',YEAR('"+format+"'),MONTH('"+format+"'),'"+format+"','请假')");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        LeaveTaskService leaveTaskService= (LeaveTaskService) SpringUtil.getObject("leaveTask");
        leaveTaskService.insertLeave(list);
    }
}
