package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface QueryMapper {


    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    String queryProStatuByProInstanceId(String processInstanceId);


    /**
     *  根据用户名查用户工号
     *  @param userName 用户名
     *  @return 用户工号
     */
    String queryCodeByName(String userName);

    /**
     *  根据用户工号查询用户在途流程数
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    Integer queryActProCountByCode(String userCode);

    /**
     *  根据用户工号查询用户结束流程数
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    Integer queryEndProCountByCode(String userCode);

    /**
     *  根据用户工号查询用户任务数
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    Integer queryTaskCountByCode(String userCode);

    /**
     *  根据用户工号查询用户任务数
     *  @param processInstanceId 流程实例ID
     *  @param userCode 用户工号
     */
    void setAssigned(String processInstanceId,String taskName,String userCode);

    /**
     *  根据角色名查询它的直接上级角色的ID
     */
    Integer queryManagerIdByRoleName(String roleName);

    /**
     *  根据用户工号查询用户审批的流程数
     *  @param userCode 用户工号
     */
    Integer queryCheckCountByCode(String userCode);

    List<Map<String,Object>> queryCheckProcessByCode(Map<String,Object> map);
}
