package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface QueryMapper {


    /**
     *  根据流程实例ID查询流程状态
     */
    String queryProStatusByProInstanceId(String processInstanceId);

    /**
     *  根据用户名查用户ID
     */
    String queryIdByName(String userName);

    /**
     *  根据用户ID查询用户在途流程数
     */
    Integer queryActProCountById(String userId);

    /**
     *  根据用户ID查询用户结束流程数
     */
    Integer queryEndProCountById(String userId);

    /**
     *  根据用户ID查询用户任务数
     */
    Integer queryTaskCountById(Integer userId);

    /**
     *  设置任务办理人
     */
    void setAssigned(String processInstanceId,String taskName,String userId);

    /**
     *  根据角色名查询它的直接上级角色的ID
     */
    Integer queryManagerIdByRoleName(String roleName);

    /**
     *  根据用户ID查询用户审批的流程数
     */
    Integer queryCheckCountById(String userId);

    /**
     *  根据用户ID查询用户审批的流程
     */
    List<Map<String,Object>> queryCheckProcessById(Map<String,Object> map);

    /**
     *  根据用户ID查询用户用户姓名
     */
    String selectNameById(String userId);

    List<Map<String,Object>> selectProcessSelect();
}
