package com.zihui.cwoa.workplan.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlanMapper {

    int insertPlan(String workMan,String planName,String timeLimit,String startTime, String planType,
                   String timeWidth,String circleList,String pointList,String project);

    List<Map<String,Object>> selectPlanName(String project);

    Map<String,Object> selectPlanText(String id);
}
