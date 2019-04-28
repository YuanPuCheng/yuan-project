package com.zihui.cwoa.financial.controller;

import com.zihui.cwoa.financial.pojo.ProjectInAndOut;
import com.zihui.cwoa.financial.pojo.ProjectMonthDetail;
import com.zihui.cwoa.financial.pojo.ProjectMonthInAndOut;
import com.zihui.cwoa.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    /**
     *  查询所有项目的总请款和总报销
     *  @return 查询结果
     */
    @RequestMapping("/queryProjectAllInAndOut")
    @ResponseBody
    public Map<String,Object> queryProjectAllInAndOut(){
        return financialService.queryProjectAllInAndOut();
    }

    /**
     *  查询项目单月总请款和总报销
     *  @return 查询结果
     */
    @RequestMapping("/queryProjectMonthInAndOut")
    @ResponseBody
    public Map<String,Object> queryProjectMonthInAndOut(String project_name){
        return financialService.queryProjectMonthInAndOut(project_name);
    }

    /**
     *  查询项目单月请款详情
     *  @return 查询结果
     */
    @RequestMapping("/queryProjectMonthOutDetail")
    @ResponseBody
    public Map<String,Object> queryProjectMonthOutDetail(String project_name, String year, String month){
        return financialService.queryProjectMonthOutDetail(project_name,year,month);
    }

    /**
     *  查询项目单月报销详情
     *  @return 查询结果
     */
    @RequestMapping("/queryProjectMonthInDetail")
    @ResponseBody
    public Map<String,Object> queryProjectMonthInDetail(String project_name, String year, String month){
        return financialService.queryProjectMonthInDetail(project_name,year,month);
    }

    /**
     *  查询所有项目的内部总报销
     *  @return 查询结果
     */
    @RequestMapping("/queryProjectAllIn")
    @ResponseBody
    public Map<String,Object> queryProjectAllIn(){

        return financialService.queryProjectAllIn();
    }

    /**
     *  查询项目内部单月总请款和总报销
     *  @return 查询结果
     */
    @RequestMapping("/queryProjectMonthIn")
    @ResponseBody
    public Map<String,Object> queryProjectMonthIn(String project_name){

        return financialService.queryProjectMonthIn(project_name);
    }

    /**
     *  查询项目内部单月报销详情
     *  @return 查询结果
     */
    @RequestMapping("/queryProMonthInDetail")
    @ResponseBody
    public Map<String,Object> queryProMonthInDetail(String project_name,String year,String month){

        return financialService.queryProMonthInDetail(project_name,year,month);
    }
}
