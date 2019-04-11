package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessWorkController {

    @Autowired
    private ProcessesService processesService;

    /**
     * 部署流程
     * @param processPath 传入流程定义文件路径
     * @return 成功/失败 true/false
     */
    @RequestMapping("/deployprocess")
    @ResponseBody
    public boolean deployProcess(String processPath) {
        processPath = "processes/askforleave.bpmn";
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
        //添加假数据
        variables.put("firstman", "Nancy");
        variables.put("secondman", "Jack");
        variables.put("processName", "请假流程");
        return processesService.startProcess("askforleave", variables);
    }

    /**
     * 查询用户任务
     * @param userCode 用户工号
     * @return 用户的任务信息
     */
    @RequestMapping("/querytask")
    @ResponseBody
    public Map<String, Object> queryTask(String userCode) {
        return processesService.queryTask(userCode);
    }

    /**
     * 根据用户工号查询他发起的还在审批中的流程
     * @param userCode 用户工号
     * @return 查询结果
     */
    @RequestMapping("/queryprocess")
    @ResponseBody
    public Map<String, Object> queryProcess(String userCode) {
        return processesService.queryProcess(userCode);
    }

    /**
     * 根据用户工号查询他发起的已经结束的流程
     * @param userCode 用户工号
     * @param page 当前页码
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
}
