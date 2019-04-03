package com.zihui.cwoa.processone.service;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AskForLeaveService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private QueryService queryService;
    /**
     *  部署流程
     *  @param processPath 传入流程定义文件路径
     *  @return 成功/失败 true/false
     */
    public boolean deployProcess(String processPath){
        try {
            Deployment deploy = this.repositoryService.createDeployment()
                    .addClasspathResource(processPath)
                    .deploy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     *  启动流程
     *  @param processKey 流程的key
     *  @param variables 添加额外的参数 供整个流程去使用(从流程开始到结束 整个时间范围内都可以获取)
     *  @return 成功/失败 true/false
     */
    public boolean startProcess(String processKey,Map<String, Object> variables){

        //业务主键 businessKey
        Long currentTimeMillis = System.currentTimeMillis();
        String businessKey=currentTimeMillis.toString();

        //添加假数据
        //variables.put("leavedays",3);
        variables.put("firstman","Nancy");
        variables.put("secondman","Jack");
        variables.put("processName","请假流程");

        //注意 在bpmn的 start节点里 要进行设置: activiti:initiator="applyuser"
        this.identityService.setAuthenticatedUserId((String)variables.get("userName"));

        //启动流程
        try {
            this.runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *  查询用户任务
     *  @param userCode 用户工号
     *  @return 用户的任务信息
     */
    public Map<String,Object> queryTask(String userCode){
        //根据用户工号获取该用户的任务
        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee(userCode)
                .list();
        Map<String,Object> map =new HashMap<>();
        if(tasks.isEmpty()){
            map.put("result","false");
        }else {
            map.put("result","true");
            List<Map<String,Object>> queryResultList = new LinkedList<>();
            for (Task task : tasks) {
                Map<String, Object> variables = taskService.getVariables(task.getId());
                variables.put("taskId",task.getId());
                String processInstanceId = task.getProcessInstanceId();
                variables.put("processInstanceId",processInstanceId);
                variables.put("startTime",runtimeService.createProcessInstanceQuery().
                        processInstanceId(processInstanceId).singleResult().getStartTime());
                queryResultList.add(variables);
            }
            map.put("queryResultList",queryResultList);
        }
        return map;
    }

    /**
     *  办理人同意流程执行到下一步
     *  @param taskId 用户工号
     *  @return 成功/失败 true/false
     */
    public boolean completeTask(String taskId){
        try {
            taskService.complete(taskId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  办理人不同意流程执行到下一步 流程强制结束
     *  @param processInstanceId 流程实例Id
     *  @param reason 不同意的理由
     *  @return 成功/失败 true/false
     */
    public boolean deleteProcessInstance(String processInstanceId,String reason){
        try {
            runtimeService.deleteProcessInstance(processInstanceId,reason);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  根据用户工号查询他发起的未完成的流程
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public List<Map<String,Object>> queryProcess(String userCode) {
        List<String> strings = queryService.queryProNotActiveByUserCode(userCode);
        List<Map<String,Object>> list = new LinkedList();
        for (String processInstanceId: strings) {
            Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
            variables.put("processStatus",queryService.queryProStatuByProInstanceId(processInstanceId));
            list.add(variables);
        }
        return list;
    }
}
