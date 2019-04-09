package com.zihui.cwoa.processone.service;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class ProcessesService {

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
            this.repositoryService.createDeployment().addClasspathResource(processPath).deploy();
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
            map.put("result",false);
        }else {
            map.put("result",true);
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
     *  根据用户工号查询他发起的还在审批中的流程
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public Map<String,Object> queryProcess(String userCode) {
        List<String> strings = queryService.queryProNotActiveByUserCode(userCode);
        List<Map<String,Object>> list = new LinkedList();
        for (String processInstanceId: strings) {
            Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
            variables.put("processStatus",queryService.queryProStatuByProInstanceId(processInstanceId));
            variables.put("processInstanceId",processInstanceId);
            list.add(variables);
        }
        Map<String,Object> map =new HashMap<>();
        if(list.isEmpty()) {
            map.put("result", false);
        }else{
            map.put("result", true);
            map.put("queryResultList",list);
        }
        return map;
    }

    /**
     *  根据用户工号查询他发起的已经结束的流程
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public  Map<String,Object> queryEndProcess(String userCode){
        List<Map<String,Object>> list = new LinkedList<>();
        List<HistoricProcessInstance> historicProcessInstanceList =
                historyService.createHistoricProcessInstanceQuery().startedBy(userCode).finished().list();
        for (HistoricProcessInstance ins:historicProcessInstanceList) {
            List<HistoricVariableInstance> hisList =
                    historyService.createHistoricVariableInstanceQuery().processInstanceId(ins.getId()).list();
            Map<String,Object> variables=new HashMap();
            for (HistoricVariableInstance hisInstance:hisList) {
                variables.put(hisInstance.getVariableName(),hisInstance.getValue());
            }
            variables.put("startTime",ins.getStartTime());
            variables.put("endTime",ins.getEndTime());
            String deleteReason = ins.getDeleteReason();
            if(deleteReason!=null){
                variables.put("deleteReason",deleteReason);
            }else{
                variables.put("deleteReason","同意申请");
            }
            list.add(variables);
        }
        Map<String,Object> map =new HashMap<>();
        if(list.isEmpty()) {
            map.put("result",false);
        }else{
            map.put("result",true);
            map.put("queryResultList",list);
        }
        return map;
    }
}