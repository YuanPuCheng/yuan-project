package com.zihui.cwoa.workplan.controller;

import com.zihui.cwoa.workplan.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/plan")
public class PlanWorkController {

    @Autowired
    private PlanService planService;

    @RequestMapping("/insertPlan")
    @ResponseBody
    public boolean insertPlan(String workMan,String planName,String circleList,String pointList){
        return planService.insertPlan(workMan,planName,circleList,pointList);
    }

    @RequestMapping("/updatePlan")
    @ResponseBody
    public boolean updatePlan(String workMan,String planName,String circleList,String pointList){
        return planService.updatePlan(workMan,planName,circleList,pointList);
    }
}
