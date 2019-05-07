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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/task")
public class taskController {

    private static Logger log = Logger.getLogger(taskController.class);

    @Resource
    private sys_taskSerivce taskSerivce;

    @Resource
    private sys_task_bService task_bService;

    @RequestMapping(value = "/gettaskPage")
    @ResponseBody
    public ConcurrentMap getrolePage(Integer page, Integer limit, HttpSession session){
        ConcurrentMap concurrentMap =new ConcurrentHashMap();
        sys_user user =(sys_user)session.getAttribute("user");
        List<sys_task> list = taskSerivce.selectTaskByPage(user.getUserId(),page,limit);
        Integer count = taskSerivce.selectTaskByPageCount(user.getUserId());
        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }



    @RequestMapping(value = "/addtask")
    @ResponseBody
    public CallbackResult addtask(sys_task task){
        CallbackResult result =new CallbackResult();
        String []users = task.getTempVar1().split(",");
        task.setTempVar1(null);
        task.setTs(DateUtils.getDate());
        task.setTaskStatus(1);
        taskSerivce.insertSelective(task);
        for(String userid:users){
            if (!Basecommon.isNullStr(userid)){
                sys_task_b task_b = new sys_task_b();
                task_b.setTaskId(task.getTaskId());
                task_b.setTaskUserIdChan(Integer.parseInt(userid));
                task_b.setTaskStatus(1);
                task_bService.insertSelective(task_b);
            }
        }
        result.setResult(200);
        result.setMessage("添加成功");
        return result;
    }
    @RequestMapping(value = "/updatestatus")
    @ResponseBody
    public CallbackResult updatestatus(@RequestParam Integer taskId){
        CallbackResult result = new CallbackResult();
        sys_task task = new sys_task();

        task.setTaskStatus(2);
        task.setTaskId(taskId);
        try {
            task_bService.updateByTaskId(taskId,2);
            taskSerivce.updateByPrimaryKeySelective(task);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("提交失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("提交成功");

        return result;
    }


    @RequestMapping(value = "/del")
    @ResponseBody
    public CallbackResult edit(@RequestParam Integer taskId){
        CallbackResult result = new CallbackResult();
        try {
            task_bService.deleteByTaskId(taskId);
            taskSerivce.deleteByPrimaryKey(taskId);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("删除失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("删除成功");

        return result;
    }

    @RequestMapping(value = "/deletes")
    @ResponseBody
    public CallbackResult deletes(@RequestParam String taskIds){
        CallbackResult result =new CallbackResult();


        String []id = taskIds.split(",");
        for (String taskId:id){
            if(!Basecommon.isNullStr(taskId)){
                try {
                    taskSerivce.deleteByPrimaryKey(Integer.parseInt(taskId));
                    task_bService.deleteByTaskId(Integer.parseInt(taskId));
                }catch (Exception e){
                    e.printStackTrace();
                    result.setResult(400);
                    result.setMessage("删除失败");
                    return result;
                }

            }
        }

        result.setResult(200);
        result.setMessage("删除成功");

        return result;
    }


    @RequestMapping(value = "/gettaskb")
    @ResponseBody
    public CallbackResult gettaskb(@RequestParam Integer taskId,@RequestParam Integer page,@RequestParam Integer limit) {
        CallbackResult result = new CallbackResult();
        try {

        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("提交失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("提交成功");

        return result;
    }
}
