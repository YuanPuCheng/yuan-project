package com.zihui.cwoa.workplan.service;

import com.zihui.cwoa.workplan.dao.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanService {

    @Autowired
    private PlanMapper planMapper;

    /**
     *  保存网络计划
     */
    public boolean insertPlan(String workMan,String planName,String timeLimit,String startTime, String planType,
                              String timeWidth,String circleList,String pointList,String project){
        return planMapper.insertPlan(workMan,planName,timeLimit,startTime,planType,timeWidth,circleList,pointList,project)>0;
    }

    /**
     *  查询网络计划名称
     */
    public List<Map<String,Object>> selectPlanName(String project){
        return planMapper.selectPlanName(project);
    }

    /**
     *  查询网络计划内容
     */
    public Map<String,Object> selectPlanText(String id){
        return planMapper.selectPlanText(id);
    }

    /**
     *  查询网络计划数量
     */
    public Integer selectPlanCount(String projectId){
        return planMapper.selectPlanCount(projectId);
    }

    /**
     *  查询网络计划信息
     */
    public Map<String,Object> selectPlanJson(String projectId,int size,int page, int limit){
        Map<String,Object> map =new HashMap<>();
        map.put("projectId",projectId);
        map.put("page",page);
        map.put("limit",limit);
        Map<String,Object> data =new HashMap<>();
        data.put("code",0);
        data.put("msg","请求成功");
        data.put("count",size);
        data.put("data",planMapper.selectPlanJson(map));
        return data;
    }

    /**
     *  批量删除网络计划
     */
    public boolean deleteManyPlan(String idArray){
        String[] split = idArray.split(",");
        return planMapper.deleteManyPlan(split)>0;
    }

    /**
     *  根据Id删除网络计划
     */
    public boolean deletePlan(String planId){
        return planMapper.deletePlan(planId)>0;
    }

    /**
     *  编辑更新网络计划信息
     */
    public boolean updatePlan(String planId,String planName,String drawMan,String drawProject,String planStatus){
        return planMapper.updatePlan(planId,planName,drawMan,drawProject,planStatus)>0;
    }
}
