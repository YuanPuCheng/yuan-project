package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.config.BpmnCreateUtil;
import com.zihui.cwoa.system.common.RedisUtils;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;

@Service("ProcessesService")
public class ProcessesService {

    public static Logger logger = Logger.getLogger(ProcessesService.class);

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
    @Resource
    private RedisUtils redisUtils;

    /**
     *  部署流程
     *  @param processPath 传入流程定义文件路径
     *  @return 成功/失败 true/false
     */
    public boolean deployProcess(String processPath){
        try {
            this.repositoryService.createDeployment().addClasspathResource(processPath).deploy();
            redisUtils.del("selectProcessSelect");
            return true;
        } catch (Exception e) {
            logger.error(e);
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
        //启动流程
        try {
            //注意 在bpmn的 start节点里 要进行设置: activiti:initiator="applyuser"
            identityService.setAuthenticatedUserId((String)variables.get("userId"));
            runtimeService.startProcessInstanceByKey(processKey, businessKey, variables);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    /**
     *  查询用户任务
     *  @param userId 用户ID
     *  @return 用户的任务信息
     */
    public Map<String,Object> queryTask(String userId,int page, int num){
        //根据用户ID获取该用户的任务
        int size = queryService.queryTaskCountById(Integer.parseInt(userId));
        Map<String,Object> map =new HashMap<>();
        map.put("result",size);
        if(size==0){
            return map;
        }
        List<Task> tasks =
                taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().listPage(page,num);
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
        return map;
    }

    /**
     *  办理人同意流程执行到下一步
     *  @param taskId 用户ID
     *  @return 成功/失败 true/false
     */
    public boolean completeTask(String taskId){
        try {
            taskService.complete(taskId);
            return true;
        } catch (Exception e) {
            logger.error(e);
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
            logger.error(e);
            return false;
        }
    }

    /**
     *  根据用户ID查询他发起的还在审批中的流程
     *  @param userId 用户ID
     *  @return 查询结果
     */
    public Map<String,Object> queryProcess(String userId,int page, int num) {
        int size = queryService.queryActProCountById(userId);
        Map<String,Object> map =new HashMap<>();
        map.put("result", size);
        if(size==0){
            return map;
        }
        List<Map<String,Object>> list = new LinkedList<>();
        List<ProcessInstance> proList =
                runtimeService.createProcessInstanceQuery().startedBy(userId).listPage(page,num);
        for (ProcessInstance pro: proList) {
            String processInstanceId=pro.getProcessInstanceId();
            Map<String, Object> variables=runtimeService.getVariables(processInstanceId);
            variables.put("processInstanceId",processInstanceId);
            variables.put("processName",pro.getProcessDefinitionName());
            variables.put("processStatus",queryService.queryProStatusByProInstanceId(processInstanceId));
            list.add(variables);
        }
        map.put("queryResultList",list);
        return map;
    }

    /**
     * 根据用户ID查询他发起的已经结束的流程
     * @param userId 用户ID
     * @param page 当前页码
     * @param num 每页显示条数
     * @return 查询结果
     */
    public  Map<String,Object> queryEndProcess(String userId,int page, int num){
        int size = queryService.queryEndProCountById(userId);
        Map<String,Object> map =new HashMap<>();
        map.put("result",size);
        if(size==0){
            return map;
        }
        List<Map<String,Object>> list = new LinkedList<>();
        List<HistoricProcessInstance> historicProcessInstanceList =
                historyService.createHistoricProcessInstanceQuery()
                        .startedBy(userId).finished().orderByProcessInstanceEndTime().desc().listPage(page,num);
        for (HistoricProcessInstance ins:historicProcessInstanceList) {
            Map<String,Object> variables=new HashMap<>();
            String processName=ins.getProcessDefinitionName();
            String processInstanceId=ins.getId();
            variables.put("processName",processName);
            if("动态任务".equals(processName)){
                String otherTalk = (String) historyService.createHistoricVariableInstanceQuery().
                        processInstanceId(processInstanceId).variableName("otherTalk").singleResult().getValue();
                variables.put("otherTalk",otherTalk);
            }
            variables.put("processInstanceId",processInstanceId);
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
        Map<String,Object> variables=new HashMap<>();
        if(hisList.isEmpty()){
            variables.put("flag",false);
            return variables;
        }else {
            variables.put("flag",true);
        }
        for (HistoricVariableInstance hisInstance:hisList) {
            variables.put(hisInstance.getVariableName(),hisInstance.getValue());
        }
        HistoricProcessInstance ins
                = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
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
        List<String> list;
        try {
            list=runtimeService.getActiveActivityIds(processInstanceId);
        } catch (Exception e) {
            return getActivityPngStream(processInstance.getDeploymentId());
        }
        DefaultProcessDiagramGenerator processDiagramGenerator= new DefaultProcessDiagramGenerator();
        return processDiagramGenerator.generateDiagram(bpmnModel, "png", list,
                Collections.<String>emptyList(), "宋体","宋体",
                "宋体", null, 1.0D);
    }

    /**
     * 查看流程图
     * @param deploymentId 流程部署Id
     * @return 流程图的输出流
     */
    public InputStream getActivityPngStream(String deploymentId) {
        ProcessDefinition processDefinition =
                repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        return repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
    }

    /**
     * 按条件查找流程
     */
    public Map<String,Object> queryProcessByVo(String processDefinitionKey,String userId
            ,Long date,int page,int num) {
        List<HistoricProcessInstance> list;
        int size;
        if (userId != null && userId!="") {
            if (date != 0) {
                Date dateOne = new Date(date-86400000);
                Date dateTwo = new Date(date+86400000);
                list = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userId)
                        .startedAfter(dateOne)
                        .startedBefore(dateTwo)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .listPage(page,num);
                size = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userId)
                        .startedAfter(dateOne)
                        .startedBefore(dateTwo)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .list()
                        .size();
            } else {
                list = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userId)
                        .orderByProcessInstanceStartTime()
                        .desc()
                        .listPage(page,num);
                size = historyService.createHistoricProcessInstanceQuery()
                        .processDefinitionKey(processDefinitionKey)
                        .startedBy(userId)
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
                Map<String,Object> variables=new HashMap<>();
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

    /**
     * 创建动态流程
     * @return 成功/失败
     */
    public boolean createLiveProcess(Map<String,Object> map){
        List<Map<String,String>> userList= (List<Map<String,String>>) map.get("userList");
        map.remove("userList");
        int i=1;
        // 1. Build up the model from scratch
        BpmnModel model = new BpmnModel();
        Process process = new Process();
        model.addProcess(process);
        process.setId("liveProcess");
        process.setName("动态任务");
        process.addFlowElement(BpmnCreateUtil.createStartEvent());
        process.addFlowElement(BpmnCreateUtil.createParallelGateway("gateway1","gateway1"));
        process.addFlowElement(BpmnCreateUtil.createParallelGateway("gateway2","gateway2"));
        process.addFlowElement(BpmnCreateUtil.createEndEvent("end"));
        process.addFlowElement(BpmnCreateUtil.createSequenceFlow("flowStart","flowStart","start", "gateway1"));
        process.addFlowElement(BpmnCreateUtil.createSequenceFlow("flowEnd", "flowEnd","gateway2","end"));
        for (Map<String,String> str: userList) {
            process.addFlowElement(BpmnCreateUtil.createUserTask("task"+i, str.get("name")+"接受任务",String.valueOf(str.get("value")),null));
            process.addFlowElement(BpmnCreateUtil.createSequenceFlow("st"+i, "st"+i,"gateway1","task"+i));
            process.addFlowElement(BpmnCreateUtil.createSequenceFlow("te"+i, "te"+i,"task"+i,"gateway2"));
            i++;
        }
        // 2. Generate graphical information
        new BpmnAutoLayout(model).execute();
        // 3. Deploy the process to the engine
        repositoryService.createDeployment().addBpmnModel("liveProcess.bpmn", model).name("动态任务").deploy();
        // 4. Start the process
        return startProcess("liveProcess",map);
    }

    /**
     * 创建多人任务
     * @return 成功/失败
     */
    public boolean startManyProcess(Map<String, Object> variables){
        //业务主键 businessKey
        Long currentTimeMillis = System.currentTimeMillis();
        String businessKey=currentTimeMillis.toString();
        List<Map<String,String>> userList= (List<Map<String,String>>) variables.get("userList");
        variables.remove("userList");
        //启动流程
        for (Map<String,String> str: userList) {
            try {
                identityService.setAuthenticatedUserId((String)variables.get("userId"));
                ProcessInstance taskProcess =
                        runtimeService.startProcessInstanceByKey("taskProcess", businessKey, variables);
                queryService.setAssigned(taskProcess.getProcessInstanceId(),str.get("name")+"接受任务",str.get("value"));
            } catch (Exception e) {
                logger.error(e);
                return false;
            }
        }
        return true;
    }

    /**
     * 拒绝动态任务
     * @return 成功/失败
     */
    public boolean rejectLiveTask(String processInstanceId, String reason,String taskId,String userId){
        String userName = queryService.selectNameById(userId);
        String otherTalk = (String) runtimeService.getVariable(processInstanceId, "otherTalk");
        if (otherTalk==null){
            otherTalk="";
        }
        otherTalk=otherTalk+"<span style=\"color:red\">"+userName+"拒绝了任务</span>:"+reason+"<br/>";
        runtimeService.setVariable(processInstanceId,"otherTalk",otherTalk);
        return completeTask(taskId);
    }

    /**
     * 根据用户ID查询他审批过的流程
     * @param userId 用户ID
     * @param page 当前页码
     * @param num 每页显示条数
     */
    public  Map<String,Object> queryCheckProcess(String userId,int page, int num){
        int size = queryService.queryCheckCountById(userId);
        Map<String,Object> map =new HashMap<>();
        map.put("result",size);
        if(size==0){
            return map;
        }
        List<Map<String,Object>> list = new LinkedList<>();
        List<Map<String, Object>> processList = queryService.queryCheckProcessById(userId, page, num);
        for (Map<String, Object> process: processList) {
            Map<String,Object> variables=new HashMap<>();
            String processName= (String) process.get("processName");
            variables.put("processName",processName);
            String processInstanceId= (String) process.get("processInstanceId");
            variables.put("processInstanceId",processInstanceId);
            variables.put("startTime",process.get("startTime"));
            variables.put("endTime",process.get("endTime"));
            variables.put("deploymentId", process.get("deploymentId"));
            variables.put("userName", process.get("userName"));
            if("动态任务".equals(processName)){
                String otherTalk = (String) historyService.createHistoricVariableInstanceQuery().
                        processInstanceId(processInstanceId).variableName("otherTalk").singleResult().getValue();
                variables.put("otherTalk",otherTalk);
            }
            String deleteReason = (String) process.get("deleteReason");
            if(deleteReason==null){
                if(process.get("endTime")==null){
                    variables.put("deleteReason","审批中");
                }else {
                    variables.put("deleteReason","同意申请");
                }
            }else{
                variables.put("deleteReason",deleteReason);
            }
            list.add(variables);
        }
        map.put("queryResultList",list);
        return map;
    }
}
