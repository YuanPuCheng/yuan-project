package com.zihui.cwoa.workplan.controller;

import com.zihui.cwoa.workplan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/plan")
public class PlanWorkController {

    @Autowired
    private PlanService planService;

    @RequestMapping("/insertPlan")
    @ResponseBody
    public boolean insertPlan(String workMan,String planName,String timeLimit,String startTime,
                              String circleList,String pointList){
        return planService.insertPlan(workMan,planName,timeLimit,startTime,circleList,pointList);
    }

    @RequestMapping("/selectPlanName")
    @ResponseBody
    public List<Map<String,Object>> selectPlanName(){
        return planService.selectPlanName();
    }

    @RequestMapping("/selectPlanText")
    @ResponseBody
    public Map<String,Object> selectPlanText(String id){
        return planService.selectPlanText(id);
    }
}
