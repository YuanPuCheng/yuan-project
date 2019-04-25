package com.zihui.cwoa.financial.controller;

import com.zihui.cwoa.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
