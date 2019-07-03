package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.ProcessesService;
import com.zihui.cwoa.processone.service.QueryService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessWorkController {

    @Autowired
    private ProcessesService processesService;

    @Resource
    private sys_userService user_service;

    @Autowired
    private QueryService queryService;

    /**
     * 部署流程
     * @param processName 传入流程定义的key
     * @return 成功/失败 true/false
     */
    @RequestMapping("/deployProcess")
    @ResponseBody
    public boolean deployProcess(String processName) {
        String processPath = "processes/"+processName+".bpmn";
        return processesService.deployProcess(processPath);
    }

    /**
     * 查询用户任务
     * @param userId 用户ID
     * @return 用户的任务信息
     */
    @RequestMapping("/queryTask")
    @ResponseBody
    public Map<String, Object> queryTask(String userId,int page, int num) {
        return processesService.queryTask(userId,page,num);
    }

    /**
     * 查询用户任务数量
     * @param userId 用户ID
     * @return 用户的任务数量
     */
    @RequestMapping("/countTask")
    @ResponseBody
    public Integer countTask(Integer userId) {
        return queryService.queryTaskCountById(userId);
    }

    /**
     * 根据用户ID查询他发起的还在审批中的流程
     * @param userId 用户ID
     * @return 查询结果
     */
    @RequestMapping("/queryProcess")
    @ResponseBody
    public Map<String, Object> queryProcess(String userId,int page, int num) {
        return processesService.queryProcess(userId,page,num);
    }

    /**
     * 根据用户ID查询他发起的已经结束的流程
     * @param userId 用户ID
     * @param page 要显示的第一条流程在数组中的编号
     * @param num 每页显示条数
     * @return 查询结果
     */
    @RequestMapping("/queryEndProcess")
    @ResponseBody
    public Map<String, Object> queryEndProcess(String userId,int page,int num) {
        return processesService.queryEndProcess(userId,page,num);
    }

    /**
     * 办理人同意流程执行到下一步
     * @param taskId 用户ID
     * @return 成功/失败 true/false
     */
    @RequestMapping("/completeTask")
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
    @RequestMapping("/deleteProcessInstance")
    @ResponseBody
    public boolean deleteProcessInstance(String processInstanceId, String reason,
                                         String taskId,String processName,String userId) {
        if ("动态任务".equals(processName)){
            return processesService.rejectLiveTask(processInstanceId, reason,taskId,userId);
        }
        return processesService.deleteProcessInstance(processInstanceId, reason);
    }

    /**
     * 查看流程详情
     * @param processInstanceId 流程实例Id
     * @return 查询结果
     */
    @RequestMapping("/queryProcessDetail")
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
    @RequestMapping("/downloadPng")
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
     * @param userId 用户id
     * @param date 日期
     * @return json
     */
    @RequestMapping("/queryProcessByVo")
    @ResponseBody
    public Map<String,Object> queryProcessByVo(String processDefinitionKey,String userId
            ,Long date,int page,int num) {
        return processesService.queryProcessByVo(processDefinitionKey,userId,date,page,num);
    }

    /**
     * 根据用户ID查询他审批过的流程
     * @param userId 用户ID
     * @param page 当前页码
     * @param num 每页显示条数
     */
    @RequestMapping("/queryCheckProcess")
    @ResponseBody
    public  Map<String,Object> queryCheckProcess(String userId,int page, int num){
        return processesService.queryCheckProcess(userId,page,num);
    }

    /**
     * 启动动态流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/startLiveProcess")
    @ResponseBody
    public boolean startLiveProcess(@RequestBody Map<String, Object> variables) {
        return processesService.createLiveProcess(variables);
    }

    /**
     * 启动动态任务
     * @return 成功/失败 true/false
     */
    @RequestMapping("/startLiveTask")
    @ResponseBody
    public boolean startLiveTask(@RequestBody Map<String, Object> variables) {
        return processesService.startManyProcess(variables);
    }

    /**
     * 启动公司请款流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askForMoney")
    @ResponseBody
    public boolean startAskForMoney(@RequestBody Map<String, Object> variables) {
        variables.put("firstMan", user_service.userRoleQuery(1, null).get(0).getUserId());
        variables.put("secondMan", user_service.userRoleQuery(12, 41).get(0).getUserId());
        return processesService.startProcess("askForMoney", variables);
    }

    /**
     * 启动公司报销流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askForReimburse")
    @ResponseBody
    public boolean startAskForReimburse(@RequestBody Map<String, Object> variables) {
        Integer projectId = Integer.parseInt((String) variables.get("projectId"));
        variables.remove("projectId");
        variables.put("firstMan", user_service.userRoleQuery(2, projectId).get(0).getUserId());
        variables.put("secondMan", user_service.userRoleQuery(12, 41).get(0).getUserId());
        return processesService.startProcess("askForReimburse", variables);
    }

    /**
     * 启动项目报销流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askForProReimburse")
    @ResponseBody
    public boolean startAskForProReimburse(@RequestBody Map<String, Object> variables) {
        Integer projectId = Integer.parseInt((String) variables.get("projectId"));
        variables.remove("projectId");
        variables.put("firstMan", user_service.userRoleQuery(2, projectId).get(0).getUserId());
        return processesService.startProcess("askForProReimburse", variables);
    }

    /**
     * 启动项目请款流程
     * @return 成功/失败 true/false
     */
    @RequestMapping("/askForProMoney")
    @ResponseBody
    public boolean startAskForProMoney(@RequestBody Map<String, Object> variables) {
        Integer projectId = Integer.parseInt((String) variables.get("projectId"));
        variables.remove("projectId");
        variables.put("firstMan", user_service.userRoleQuery(2, projectId).get(0).getUserId());
        return processesService.startProcess("askForProMoney", variables);
    }

    /**
     * 启动请假或者出差流程
     * @param variables 流程变量
     * @return 成功/失败 true/false
     */
    @RequestMapping("/startAskForLeave")
    @ResponseBody
    public boolean startAskForLeave(@RequestBody Map<String, Object> variables) {
        String processKey=(String) variables.get("processKey");
        variables.remove("processKey");
        String projectId = (String) variables.get("projectId");
        variables.remove("projectId");
        String roleName = (String) variables.get("userRole");
        variables.remove("userRole");
        String[] split = roleName.split("、");
        Integer roleId=999;
        for (String str: split) {
            Integer tempId=queryService.queryManagerIdByRoleName(str);
            if (tempId<roleId){
                roleId=tempId;
            }
        }
        Integer upProjectId= Integer.parseInt(projectId);
        if (roleId<=2){
            if (roleId==1){
                upProjectId=41;
            }
            if ("askForLeave".equals(processKey)){
                processKey="highAskForLeave";
            }else {
                processKey="highAskForTravel";
            }
        }
        variables.put("firstMan", user_service.userRoleQuery(roleId,upProjectId).get(0).getUserId());
        variables.put("secondMan", user_service.userRoleQuery(2,Integer.parseInt(projectId)).get(0).getUserId());
        return processesService.startProcess(processKey, variables);
    }

    /**
     * 条件查询流程下拉菜单
     * @return json
     */
    @RequestMapping("/selectProcessSelect")
    @ResponseBody
    public List<Map<String,Object>> selectProcessSelect(){
        return queryService.selectProcessSelect();
    }

    /**
     * 启动借车流程
     * @return text
     */
    @RequestMapping("/getCar")
    @ResponseBody
    public boolean startGetCar(@RequestBody Map<String, Object> variables) {
        return processesService.startProcess("getCar", variables);
    }

    /**
     * 启动还车流程
     * @return text
     */
    @RequestMapping("/returnCar")
    @ResponseBody
    public boolean startReturnCar(@RequestBody Map<String, Object> variables) {
        return processesService.startProcess("returnCar", variables);
    }
}
