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

    /**
     *  保存网络计划
     *  @return text
     */
    @RequestMapping("/insertPlan")
    @ResponseBody
    public boolean insertPlan(String workMan,String planName,String timeLimit,String startTime, String planType,
                              String timeWidth,String circleList,String pointList,String project){
        return planService.insertPlan(workMan,planName,timeLimit,startTime,planType,timeWidth,circleList,pointList,project);
    }

    /**
     *  查询网络计划名称,打开的下拉选项
     *  @return json
     */
    @RequestMapping("/selectPlanName")
    @ResponseBody
    public List<Map<String,Object>> selectPlanName(String project){
        return planService.selectPlanName(project);
    }

    /**
     *  网络计划的具体内容
     *  @return json
     */
    @RequestMapping("/selectPlanText")
    @ResponseBody
    public Map<String,Object> selectPlanText(String id){
        return planService.selectPlanText(id);
    }

    /**
     *  查询网络计划数量
     *  @return text
     */
    @RequestMapping("/selectPlanCount")
    @ResponseBody
    public Integer selectPlanCount(String projectId){
        return planService.selectPlanCount(projectId);
    }

    /**
     *  查询网络计划名称 创建时间 管理用
     *  @return json
     */
    @RequestMapping("/selectPlanJson")
    @ResponseBody
    public Map<String,Object> selectPlanJson(String projectId,int size,int page, int limit){
        page=(page-1)*limit;
        return planService.selectPlanJson(projectId,size,page,limit);
    }

    /**
     *  批量删除网络计划
     *  @return text
     */
    @RequestMapping("/deleteManyPlan")
    @ResponseBody
    public boolean deleteManyPlan(String idArray){
        return planService.deleteManyPlan(idArray);
    }

    /**
     *  根据Id删除网络计划
     *  @return text
     */
    @RequestMapping("/deletePlan")
    @ResponseBody
    public boolean deletePlan(String planId){
        return planService.deletePlan(planId);
    }

    /**
     *  编辑更新网络计划信息
     *  @return text
     */
    @RequestMapping("/updatePlan")
    @ResponseBody
    public boolean updatePlan(String planId,String planName,String drawMan,String drawProject,String planStatus){
        return planService.updatePlan(planId,planName,drawMan,drawProject,planStatus);
    }
}
