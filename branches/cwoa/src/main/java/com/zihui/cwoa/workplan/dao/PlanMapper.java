package com.zihui.cwoa.workplan.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PlanMapper {

    int insertPlan(String workMan,String planName,String circleList,String pointList);

    int updatePlan(String workMan,String planName,String circleList,String pointList);
}
