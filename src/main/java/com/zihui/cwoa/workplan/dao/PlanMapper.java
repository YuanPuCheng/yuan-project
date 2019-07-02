package com.zihui.cwoa.workplan.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlanMapper {

    /**
     *  保存网络计划
     */
    int insertPlan(String workMan,String planName,String timeLimit,String startTime, String planType,
                   String timeWidth,String circleList,String pointList,String project);

    /**
     *  查询网络计划名称
     */
    List<Map<String,Object>> selectPlanName(String project);

    /**
     *  查询网络计划内容
     */
    Map<String,Object> selectPlanText(String id);

    /**
     *  查询网络计划数量
     */
    Integer selectPlanCount(@Param("projectId") String projectId);

    /**
     *  查询网络计划信息
     */
    List<Map<String,Object>> selectPlanJson(Map<String,Object> map);

    /**
     *  批量删除网络计划
     */
    int deleteManyPlan(String[] split);

    /**
     *  根据Id删除网络计划
     */
    int deletePlan(String planId);

    /**
     *  编辑更新网络计划信息
     */
    int updatePlan(String planId,String planName,String drawMan,String drawProject,String planStatus);
}
