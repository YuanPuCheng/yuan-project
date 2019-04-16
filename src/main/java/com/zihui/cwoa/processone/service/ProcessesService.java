package com.zihui.cwoa.processone.service;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
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
    //自定义的查询方法
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
        this.identityService.setAuthenticatedUserId((String)variables.get("userCode"));
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
        List<Task> tasks =
                taskService.createTaskQuery().taskAssignee(userCode).orderByTaskCreateTime().desc().list();
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
                ProcessInstance processInstance =
                        runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
                variables.put("startTime",processInstance.getStartTime());
                variables.put("processName",processInstance.getProcessDefinitionName());
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
        //自定义方法 查询用户在途的流程Id
        List<String> strings = queryService.queryProcessActiveByUserCode(userCode);
        List<Map<String,Object>> list = new LinkedList();
        for (String processInstanceId: strings) {
            Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
            //自定义方法 查询用户在途的流程节点
            variables.put("processStatus",queryService.queryProStatuByProInstanceId(processInstanceId));
            variables.put("processInstanceId",processInstanceId);
            variables.put("processName",queryService.queryProNameByProInstanceId(processInstanceId));
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
     * 根据用户工号查询他发起的已经结束的流程
     * @param userCode 用户工号
     * @param page 当前页码
     * @param num 每页显示条数
     * @return 查询结果
     */
    public  Map<String,Object> queryEndProcess(String userCode,int page, int num){
        List<Map<String,Object>> list = new LinkedList<>();
        List<HistoricProcessInstance> historicProcessInstanceList =
                historyService.createHistoricProcessInstanceQuery()
                        .startedBy(userCode).finished().orderByProcessInstanceEndTime().desc().listPage(page,num);
        int size = historyService.createHistoricProcessInstanceQuery()
                .startedBy(userCode).finished().list().size();
        for (HistoricProcessInstance ins:historicProcessInstanceList) {
            Map<String,Object> variables=new HashMap();
            variables.put("processName",ins.getProcessDefinitionName());
            variables.put("processInstanceId",ins.getId());
            variables.put("startTime",ins.getStartTime());
            variables.put("endTime",ins.getEndTime());
            variables.put("deploymentId", ins.getDeploymentId());
            String deleteReason = ins.getDeleteReason();
            if(deleteReason!=null){
                variables.put("deleteReason",deleteReason);
            }else{
                variables.put("deleteReason","同意申请");
            }
            list.add(variables);
        }
        Map<String,Object> map =new HashMap<>();
        map.put("result",size);
        map.put("queryResultList",list);
        return map;
    }

    /**
     *  查看流程详情
     *  @param processInstanceId 流程实例Id
     *  @return 查询结果
     */
    public  Map<String,Object> queryProcessDetail(String processInstanceId){
        List<HistoricVariableInstance> hisList =
                historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        Map<String,Object> variables=new HashMap();
        if(hisList.isEmpty()){
            variables.put("flag",false);
            return variables;
        }else {
            variables.put("flag",true);
        }
        for (HistoricVariableInstance hisInstance:hisList) {
            variables.put(hisInstance.getVariableName(),hisInstance.getValue());
        }
        List<HistoricProcessInstance> list =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).list();
        for (HistoricProcessInstance ins:list) {
            variables.put("startTime",ins.getStartTime());
            Date endTime = ins.getEndTime();
            variables.put("endTime",endTime);
            variables.put("processInstanceId",processInstanceId);
            variables.put("processName",ins.getProcessDefinitionName());
            String deleteReason = ins.getDeleteReason();
            if(deleteReason!=null){
                variables.put("deleteReason",deleteReason);
            }else if(endTime!=null){
                variables.put("deleteReason","同意申请");
            }else{
                variables.put("deleteReason","审批中");
            }
        }
        return variables;
    }

    /**
     * 查看带节点的流程图
     * @param processInstanceId 流程实例Id
     * @return 流程图的输出流
     */
    public InputStream getPngStream(String processInstanceId) {
        // 获得流程实例
        HistoricProcessInstance processInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        // 获得流程定义id
        String processDefinitionId = processInstance.getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        List<String> list=null;
        try {
            list=runtimeService.getActiveActivityIds(processInstanceId);
        } catch (Exception e) {
            return getActivityPngStream(processInstance.getDeploymentId());
        }
        DefaultProcessDiagramGenerator processDiagramGenerator= new DefaultProcessDiagramGenerator();
        InputStream png =
                processDiagramGenerator.generateDiagram(bpmnModel, "png", list,
                        Collections.<String>emptyList(), "宋体","宋体",
                        "宋体", null, 1.0D);
        return png;
    }

    /**
     * 查看流程图
     * @param deploymentId 流程部署Id
     * @return 流程图的输出流
     */
    public InputStream getActivityPngStream(String deploymentId) {
        ProcessDefinition processDefinition =
                repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        InputStream is =
                repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
        return is;
    }

    /**
     * 按条件查找流程
     * @param processDefinitionKey 流程定义的Key
     * @param userName 用户名
     * @param date 日期
     * @return 查询结果
     */
    public Map<String,Object> queryProcessByVo(String processDefinitionKey,String userName
            ,Long date,int page,int num) {
        List<HistoricProcessInstance> list=null;
        String userCode=null;
        int size=0;
        if(userName!="" && userName!=null){
            List<String> userCodeList = queryService.queryCodeByName(userName);
            if (userCodeList.size()==1){
                userCode=userCodeList.get(0);
            }
        }
        if (userCode != null) {
            if (date != 0) {
                Date dateOne = new Date(date-86400000);
                Date dateTwo = new Date(date+86400000);
                list = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userCode)
                        .startedAfter(dateOne)
                        .startedBefore(dateTwo)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .listPage(page,num);
                size = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userCode)
                        .startedAfter(dateOne)
                        .startedBefore(dateTwo)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .list()
                        .size();
            } else {
                list = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userCode)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .listPage(page,num);
                size = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userCode)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .list()
                        .size();
            }
        } else if (date != 0) {
            Date dateOne = new Date(date-86400000);
            Date dateTwo = new Date(date+86400000);
            list = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .startedAfter(dateOne)
                    .startedBefore(dateTwo)
                    .orderByProcessInstanceStartTime()
                    .desc()
                    .listPage(page,num);
            size = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .startedAfter(dateOne)
                    .startedBefore(dateTwo)
                    .orderByProcessInstanceStartTime()
                    .desc()
                    .list()
                    .size();
        } else {
            list = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .orderByProcessInstanceStartTime()
                    .desc()
                    .listPage(page,num);
            size = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey(processDefinitionKey)
                    .orderByProcessInstanceStartTime()
                    .desc()
                    .list()
                    .size();
        }
        List<Map<String,Object>> resultList = new LinkedList<>();
        for (HistoricProcessInstance ins : list) {
                String processInstanceId=ins.getId();
                List<HistoricVariableInstance> hisList =
                        historyService.createHistoricVariableInstanceQuery()
                                .processInstanceId(processInstanceId).list();
                Map<String,Object> variables=new HashMap();
                for (HistoricVariableInstance hisInstance:hisList) {
                    variables.put(hisInstance.getVariableName(),hisInstance.getValue());
                }
                variables.put("startTime", ins.getStartTime());
                Date endTime = ins.getEndTime();
                variables.put("endTime", endTime);
                variables.put("processInstanceId", processInstanceId);
                variables.put("processName", ins.getProcessDefinitionName());
                String deleteReason = ins.getDeleteReason();
                if (endTime == null) {
                    variables.put("deleteReason", "审批中");
                } else if (deleteReason!=null) {
                    variables.put("deleteReason", deleteReason);
                } else {
                    variables.put("deleteReason", "同意申请");
                }
                resultList.add(variables);
        }
        Map<String,Object> map =new HashMap<>();
        map.put("result",size);
        map.put("queryResultList",resultList);
        return map;
    }
}
