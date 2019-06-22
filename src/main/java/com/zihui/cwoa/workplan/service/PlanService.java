package com.zihui.cwoa.workplan.service;

import com.zihui.cwoa.workplan.dao.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlanService {

    @Autowired
    private PlanMapper planMapper;

    public boolean insertPlan(String workMan,String planName,String timeLimit,String startTime, String planType,
                              String timeWidth,String circleList,String pointList,String project){
        return planMapper.insertPlan(workMan,planName,timeLimit,startTime,planType,timeWidth,circleList,pointList,project)>0;
    }


    public List<Map<String,Object>> selectPlanName(String project){
        return planMapper.selectPlanName(project);
    }

    public Map<String,Object> selectPlanText(String id){
        return planMapper.selectPlanText(id);
    }
}
