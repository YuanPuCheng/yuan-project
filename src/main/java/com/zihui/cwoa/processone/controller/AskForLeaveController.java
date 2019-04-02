package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.AskForLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/askforleave")
public class AskForLeaveController {

    @Autowired
    private AskForLeaveService askForLeaveService;

    @RequestMapping("/deployprocess")
    @ResponseBody
    public boolean deployProcess(){
        return askForLeaveService.deployProcess("processes/askforleave.bpmn");
    }

    @RequestMapping("/startprocess")
    @ResponseBody
    public boolean startProcess(){
        Map<String, Object> map =new HashMap();
        map.put("firstman","Nancy");
        map.put("leavedays",5);
        map.put("secondman","Jack");
        return askForLeaveService.startProcess("askforleave","CarlBryant",map);
    }

    @RequestMapping("/querytask")
    @ResponseBody
    public Map<String,Object> queryTask(String userCode){
        return askForLeaveService.queryTask(userCode);
    }

    @RequestMapping("/completetask")
    @ResponseBody
    public boolean completeTask(String taskId){
        return askForLeaveService.completeTask(taskId);
    }

    @RequestMapping("/deleteprocessinstance")
    @ResponseBody
    public boolean deleteProcessInstance(String processInstanceId){
        String reason="没有理由";
        return askForLeaveService.deleteProcessInstance(processInstanceId,reason);
    }

}
