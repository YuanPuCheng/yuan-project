package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.ProcessesService;
import com.zihui.cwoa.system.pojo.sys_users;
import com.zihui.cwoa.system.service.sys_userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessWorkController {

    @Autowired
    private ProcessesService processesService;

    @Resource
    private sys_userService user_service;

    /**
     * 部署流程
     * @param processName 传入流程定义的key
     * @return 成功/失败 true/false
     */
    @RequestMapping("/deployprocess")
    @ResponseBody
    public boolean deployProcess(String processName) {
        String processPath = "processes/"+processName+".bpmn";
        return processesService.deployProcess(processPath);
    }

    /**
     * 启动请假流程
     * @param variables 流程变量
     * @return 成功/失败 true/false
     */
    @RequestMapping("/startaskforleave")
    @ResponseBody
    public boolean startAskForLeave(@RequestBody Map<String, Object> variables) {
        Integer projectId = (Integer) variables.get("projectId");
        variables.remove("projectId");
        Integer roleId = (Integer) variables.get("roleId");
        variables.remove("roleId");
        List<sys_users> sys_users1 = user_service.userRoleQuery(roleId, projectId);
        String userCode1 = sys_users1.get(0).getUserCode();
        List<sys_users> sys_users2 = user_service.userRoleQuery(2, projectId);
        String userCode2 = sys_users2.get(0).getUserCode();
        variables.put("firstman", userCode1);
        variables.put("secondman", userCode2);
        return processesService.startProcess("askforleave", variables);
    }

    /**
     * 启动出差流程
     * @param variables 流程变量
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askforbusiness")
    @ResponseBody
    public boolean askForBusiness(@RequestBody Map<String, Object> variables) {
        //添加假数据
        variables.put("firstman", "Nancy");
        variables.put("secondman", "Nancy");
        return processesService.startProcess("askforbusiness", variables);
    }
    /**
     * 查询用户任务
     * @param userCode 用户工号
     * @return 用户的任务信息
     */
    @RequestMapping("/querytask")
    @ResponseBody
    public Map<String, Object> queryTask(String userCode,int page, int num) {
        return processesService.queryTask(userCode,page,num);
    }

    /**
     * 根据用户工号查询他发起的还在审批中的流程
     * @param userCode 用户工号
     * @return 查询结果
     */
    @RequestMapping("/queryprocess")
    @ResponseBody
    public Map<String, Object> queryProcess(String userCode,int page, int num) {
        return processesService.queryProcess(userCode,page,num);
    }

    /**
     * 根据用户工号查询他发起的已经结束的流程
     * @param userCode 用户工号
     * @param page 要显示的第一条流程在数组中的编号
     * @param num 每页显示条数
     * @return 查询结果
     */
    @RequestMapping("/queryendprocess")
    @ResponseBody
    public Map<String, Object> queryEndProcess(String userCode,int page,int num) {
        return processesService.queryEndProcess(userCode,page,num);
    }

    /**
     * 办理人同意流程执行到下一步
     * @param taskId 用户工号
     * @return 成功/失败 true/false
     */
    @RequestMapping("/completetask")
    @ResponseBody
    public boolean completeTask(String taskId) {
        return processesService.completeTask(taskId);
    }

    /**
     * 办理人不同意流程执行到下一步 流程强制结束
     * @param processInstanceId 流程实例Id
     * @param reason            不同意的理由
     * @return 成功/失败 true/false
     */
    @RequestMapping("/deleteprocessinstance")
    @ResponseBody
    public boolean deleteProcessInstance(String processInstanceId, String reason) {
        return processesService.deleteProcessInstance(processInstanceId, reason);
    }

    /**
     * 查看流程详情
     * @param processInstanceId 流程实例Id
     * @return 查询结果
     */
    @RequestMapping("/queryprocessdetail")
    @ResponseBody
    public Map<String, Object> queryProcessDetail(String processInstanceId) {
        return processesService.queryProcessDetail(processInstanceId);
    }

    /**
     * 查看带节点的流程图
     * @param processInstanceId 流程实例Id
     * @return 流程图的输出流
     */
    @RequestMapping("/download")
    public String downloadFile(HttpServletResponse response, String processInstanceId) {
        Long currentTimeMillis = System.currentTimeMillis();
        String fileName=currentTimeMillis.toString();
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        byte[] buffer = new byte[1024];
        InputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = processesService.getPngStream(processInstanceId);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 查看流程图
     * @param deploymentId 流程部署Id
     * @return 流程图的输出流
     */
    @RequestMapping("/downloadpng")
    public String downloadPng(HttpServletResponse response, String deploymentId) {
        Long currentTimeMillis = System.currentTimeMillis();
        String fileName=currentTimeMillis.toString();
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        byte[] buffer = new byte[1024];
        InputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = processesService.getActivityPngStream(deploymentId);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    /**
     * 按条件查找流程
     * @param processDefinitionKey 流程定义的Key
     * @param userName 用户名字
     * @param date 日期
     * @return 查询结果
     */
    @RequestMapping("/queryprocessbyvo")
    @ResponseBody
    public Map<String,Object> queryProcessByVo(String processDefinitionKey,String userName
            ,Long date,int page,int num) {
        return processesService.queryProcessByVo(processDefinitionKey,userName,date,page,num);
    }

    /**
     * 启动动态流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/startliveprocess")
    @ResponseBody
    public boolean startLiveProcess(@RequestBody Map<String, Object> variables) {
        return processesService.createLiveProcess(variables);
    }

    /**
     * 启动动态任务
     * @return 成功/失败 true/false
     */
    @RequestMapping("/startlivetask")
    @ResponseBody
    public boolean startLiveTask(@RequestBody Map<String, Object> variables) {
        return processesService.startManyProcess(variables);
    }

    /**
     * 启动请款流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askformoney")
    @ResponseBody
    public boolean startAskForMoney(@RequestBody Map<String, Object> variables) {
        //添加假数据
        variables.put("firstman", "Nancy");
        variables.put("secondman", "Nancy");
        return processesService.startProcess("askformoney", variables);
    }

    /**
     * 启动公司报销流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askforreimburse")
    @ResponseBody
    public boolean startAskForReimburse(@RequestBody Map<String, Object> variables) {
        //添加假数据
        variables.put("firstman", "Nancy");
        variables.put("secondman", "Nancy");
        return processesService.startProcess("askforreimburse", variables);
    }

    /**
     * 启动项目报销流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askforproreimburse")
    @ResponseBody
    public boolean startAskForProReimburse(@RequestBody Map<String, Object> variables) {
        //添加假数据
        variables.put("firstman", "Nancy");
        variables.put("secondman", "Nancy");
        return processesService.startProcess("askforproreimburse", variables);
    }

}
