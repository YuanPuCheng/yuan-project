package com.zihui.cwoa.processone.config;

import org.activiti.bpmn.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  流程图创建工具
 */
public class BpmnCreateUtil {
	 
	/**
	 * 创建开始节点
	 */
	public static FlowElement createStartEvent() {
		StartEvent startEvent = new StartEvent();
		startEvent.setId("start");	
		startEvent.setInitiator("applyuser");
		return startEvent;
	}
	
	/**
	 * 创建用户任务
	 * @param id taskDefinitionID 任务定义ID
	 * @param name 任务名称
	 * @param assignee 执行人ID
	 * @return UserTask
	 */
	public static UserTask createUserTask(String id, String name, String assignee, List<String> groups){
		UserTask userTask = new UserTask();
		userTask.setId(id);
		userTask.setName(name);
		if(!StringUtils.isBlank(assignee)){
			userTask.setAssignee(assignee);
		}
		
		if(null != groups && groups.size() > 0){
			userTask.setCandidateGroups(groups);
		}		
		
		return userTask;
	}
	
	/**
	 * 为用户任务添加监听事件
	 * @param userTask 用户任务
	 * @param className 事件
	 * @param eventType 事件类型
	 */
	public static void addTaskListener(UserTask userTask, String eventType, String className){
		
		List<ActivitiListener> executionListeners = userTask.getExecutionListeners();
		ActivitiListener act = new ActivitiListener();
		act.setEvent(eventType);
		act.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
		act.setImplementation(className);	
		
		if(null != executionListeners && executionListeners.size() >0) {
			executionListeners.add(act);
		} else {
			executionListeners = new ArrayList<ActivitiListener>();
			executionListeners.add(act);
		}
		userTask.setTaskListeners(executionListeners);
		
	}
	
	/**
	 * 创建服务任务
	 * @param id 任务定义ID
	 * @param name 任务名称
	 * @param className 调用类
	 * @return ServiceTask
	 */
	public static ServiceTask createServiceTask(String id, String name, String className) {
		ServiceTask serviceTask = new ServiceTask();
		serviceTask.setId(id);
		serviceTask.setName(name);
		serviceTask.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_CLASS);
		serviceTask.setImplementation(className);
		return serviceTask;
	}
	
	/**
	 * 创建顺序流
	 * @param id 顺序流ID 
	 * @param name 顺序流名称
	 * @param from 起始节点
	 * @param to 目标节点
	 * @return SequenceFlow
	 */
	public static SequenceFlow createSequenceFlow(String id, String name, String from, String to){
		SequenceFlow sequenceFlow = new SequenceFlow();
		sequenceFlow.setId(id);
		sequenceFlow.setName(name);
		sequenceFlow.setSourceRef(from);
		sequenceFlow.setTargetRef(to);
		return sequenceFlow;
	}
	
	/**
	 * 创建顺序流 + 顺序流 condition参数
	 * @param id 顺序流ID 
	 * @param name 顺序流名称
	 * @param from 起始节点
	 * @param to 目标节点
	 * @param conditionExpression 条件表达式
	 * @return SequenceFlow
	 */
	public static SequenceFlow createSequenceFlowWithExpression(String id, String name, String from, String to, String conditionExpression){
		SequenceFlow sequenceFlow = new SequenceFlow();
		sequenceFlow.setId(id);
		sequenceFlow.setName(name);
		sequenceFlow.setSourceRef(from);
		sequenceFlow.setTargetRef(to);
		sequenceFlow.setConditionExpression(conditionExpression);
		return sequenceFlow;
	}
	
	/**
	 * 创建分支网关
	 * @param id 网关ID
	 * @param name 网关名称
	 * @return FlowElement --> ExclusiveGateway
	 */
	public static ExclusiveGateway createExclusiveGateway(String id, String name){
		ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
		exclusiveGateway.setId(id);
		exclusiveGateway.setName(name);
		return exclusiveGateway;
	}
	
	/**
	 * 创建结束节点
	 * @return FlowElement
	 */
	public static FlowElement createEndEvent(String id) {
		EndEvent endEvent = new EndEvent();
		endEvent.setId(id);
		return endEvent;
	}

	/**
	 * 创建并行网关
	 * @param id 网关ID
	 * @param name 网关名称
	 * @return FlowElement --> ExclusiveGateway
	 */
	public static ParallelGateway createParallelGateway(String id, String name){
		ParallelGateway parallelGateway=new ParallelGateway();
		parallelGateway.setId(id);
		parallelGateway.setName(name);
		return parallelGateway;
	}
	
}
