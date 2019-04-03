package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.AskForLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public boolean startProcess(@RequestBody Map<String,Object> map){
        return askForLeaveService.startProcess("askforleave",map);
    }

    @RequestMapping("/querytask")
    @ResponseBody
    public Map<String,Object> queryTask(String userCode){
        return askForLeaveService.queryTask(userCode);
    }

    @RequestMapping("/queryprocess")
    @ResponseBody
    public List queryProcess(String userCode){
        return askForLeaveService.queryProcess(userCode);
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

    @RequestMapping("/testone")
    @ResponseBody
    public boolean TestOne(@RequestBody Map<String,Object> map){
        //Map<String,Object> map=new HashMap<>();
        //map.forEach((k,v)-> System.out.println(k+"="+v));
        //List<Map<String,Object>> processSummary = (List<Map<String, Object>>) map.get("processSummary");
        System.out.println(map);
        //map.put("result","true");
        return true;
    }
}
