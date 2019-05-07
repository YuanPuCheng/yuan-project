package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.pojo.sys_task;
import com.zihui.cwoa.system.pojo.sys_task_b;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_taskSerivce;
import com.zihui.cwoa.system.service.sys_task_bService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/mytask")
public class MyTaskController {

    private static Logger log = Logger.getLogger(MyTaskController.class);

    @Resource
    private sys_taskSerivce taskSerivce;

    @Resource
    private sys_task_bService task_bService;


    @RequestMapping(value = "/getMytaskPage")
    @ResponseBody
    public ConcurrentMap getrolePage(Integer page, Integer limit, HttpSession session){
        ConcurrentMap concurrentMap =new ConcurrentHashMap();
        sys_user user = (sys_user) session.getAttribute("user");
        List<sys_task> list = taskSerivce.myTaskbyQuery(user.getUserId(),page,limit);
        Integer count = taskSerivce.myTaskbyQueryCount(user.getUserId());
        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


    @RequestMapping(value = "/addmytask")
    @ResponseBody
    public CallbackResult addmytask(sys_task_b task){
        CallbackResult result =new CallbackResult();
        task.setTaskStatus(4);
        task.setTaskEndTime(DateUtils.getDate());
        Integer taskId = task.getTaskId();
        task.setTaskId(null);
        List<sys_task_b> task_bs = task_bService.taskbQuery(taskId);
        Integer count = 0;
        Integer size = task_bs.size();
        for(sys_task_b b:task_bs){
            //判断所有任务人的状态是否为完成
            if(b.getTaskStatus()==4){
                count+=1;
            };
        }
        //判断是否是最后一人完成任务
        if((size-count)==1){
            sys_task task1 = new sys_task();
            task1.setTaskId(taskId);
            task1.setTaskStatus(3);
            log.info("设置任务完成");
            taskSerivce.updateByPrimaryKeySelective(task1);
        }
        try{
            task_bService.updateByPrimaryKeySelective(task);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("系统错误，请联系管理员");
        }
        result.setResult(200);
        result.setMessage("已完成任务");
        return result;
    }


    @RequestMapping(value = "/mytaskstatus")
    @ResponseBody
    public CallbackResult mytaskstatus(Integer taskBId){
        CallbackResult result =new CallbackResult();
        sys_task_b task = new sys_task_b();
        task.setTaskBId(taskBId);
        task.setTaskStatus(3);
        task.setTaskLookTime(DateUtils.getDate());
        try{
            task_bService.updateByPrimaryKeySelective(task);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("系统错误，请联系管理员");
        }
        result.setResult(200);
        result.setMessage("");
        return result;
    }


}
