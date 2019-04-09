package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class AskForLeaveController {

    @Autowired
    private ProcessesService processesService;

    /**
     *  部署流程
     *  @param processPath 传入流程定义文件路径
     *  @return 成功/失败 true/false
     */
    @RequestMapping("/deployprocess")
    @ResponseBody
    public boolean deployProcess(String processPath){
        processPath= "processes/askforleave.bpmn";
        return processesService.deployProcess(processPath);
    }

    /**
     *  启动请假流程
     *  @param variables 流程变量
     *  @return 成功/失败 true/false
     */
    @RequestMapping("/startaskforleave")
    @ResponseBody
    public boolean startAskForLeave(@RequestBody Map<String,Object> variables){
        //添加假数据
        //variables.put("leavedays",3);
        variables.put("firstman","Nancy");
        variables.put("secondman","Jack");
        variables.put("processName","请假流程");
        return processesService.startProcess("askforleave",variables);
    }

    /**
     *  查询用户任务
     *  @param userCode 用户工号
     *  @return 用户的任务信息
     */
    @RequestMapping("/querytask")
    @ResponseBody
    public Map<String,Object> queryTask(String userCode){
        return processesService.queryTask(userCode);
    }

    /**
     *  根据用户工号查询他发起的还在审批中的流程
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    @RequestMapping("/queryprocess")
    @ResponseBody
    public Map<String,Object> queryProcess(String userCode){
        return processesService.queryProcess(userCode);
    }

    /**
     *  根据用户工号查询他发起的已经结束的流程
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    @RequestMapping("/queryendprocess")
    @ResponseBody
    public Map<String,Object> queryEndProcess(String userCode){
        return processesService.queryEndProcess(userCode);
    }

    /**
     *  办理人同意流程执行到下一步
     *  @param taskId 用户工号
     *  @return 成功/失败 true/false
     */
    @RequestMapping("/completetask")
    @ResponseBody
    public boolean completeTask(String taskId){
        return processesService.completeTask(taskId);
    }

    /**
     *  办理人不同意流程执行到下一步 流程强制结束
     *  @param processInstanceId 流程实例Id
     *  @param reason 不同意的理由
     *  @return 成功/失败 true/false
     */
    @RequestMapping("/deleteprocessinstance")
    @ResponseBody
    public boolean deleteProcessInstance(String processInstanceId,String reason){
        return processesService.deleteProcessInstance(processInstanceId,reason);
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

    @RequestMapping("/testtwo")
    @ResponseBody
    public boolean TestTwo(HttpSession httpSession){

        System.out.println("验证码为："+httpSession.getAttribute("vrifyCode"));
        return true;
    }

    @RequestMapping("/testthree")
    public String TestThree(){
        return "fileseven";
    }
}
