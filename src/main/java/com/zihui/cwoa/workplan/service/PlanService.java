package com.zihui.cwoa.workplan.service;

import com.zihui.cwoa.workplan.dao.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    @Autowired
    private PlanMapper planMapper;

    public boolean insertPlan(String workMan,String planName,String circleList,String pointList){
        return planMapper.insertPlan(workMan,planName,circleList,pointList)>0;
    }

    public boolean updatePlan(String workMan,String planName,String circleList,String pointList){
        return planMapper.updatePlan(workMan,planName,circleList,pointList)>0;
    }
}
