package com.zihui.cwoa.financial.controller;

import com.zihui.cwoa.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    /**
     *  根据条件查询公司请销款记录
     *  @return 查询结果
     */
    @RequestMapping("/queryMoneyFlowByVoc")
    @ResponseBody
    public Map<String,Object> queryMoneyFlowByVoc(int size, String userId, String project,
                                                  String flowYear, String flowMonth, String flowType,
                                                  int page, int limit){
        page=(page-1)*limit;
        return financialService.queryMoneyFlowByVoc(size,userId,project,flowYear,flowMonth,flowType,page,limit);
    }

    /**
     *  根据条件查询项目请销款记录
     *  @return 查询结果
     */
    @RequestMapping("/queryMoneyFlowByVop")
    @ResponseBody
    public Map<String,Object> queryMoneyFlowByVop(int size, String userId, String project,
                                                  String flowYear, String flowMonth, String flowType,
                                                  int page, int limit){
        page=(page-1)*limit;
        return financialService.queryMoneyFlowByVop(size,userId,project,flowYear,flowMonth,flowType,page,limit);
    }

    /**
     *  根据条件查询项目请销款记录数
     *  @return 查询结果
     */
    @RequestMapping("/countMoneyFlowByVop")
    @ResponseBody
    public Map<String,Object> countMoneyFlowByVop(String userId, String project, String flowYear,
                                       String flowMonth, String flowType){
        Map<String,Object> map =new HashMap<>();
        map.put("userId",userId);
        map.put("count",financialService.countMoneyFlowByVop(userId,project,flowYear,flowMonth,flowType));
        return map;
    }

    /**
     *  根据条件查询公司请销款记录数
     *  @return 查询结果
     */
    @RequestMapping("/countMoneyFlowByVoc")
    @ResponseBody
    public Map<String,Object> countMoneyFlowByVoc(String userId, String project, String flowYear,
                                                  String flowMonth, String flowType){
        Map<String,Object> map =new HashMap<>();
        map.put("count",financialService.countMoneyFlowByVoc(userId,project,flowYear,flowMonth,flowType));
        return map;
    }

    /**
     *  根据条件分析项目请销款记录
     *  @return 查询结果
     */
    @RequestMapping("/queryMoneyFlowSumByVo")
    @ResponseBody
    public Map<String,Object> queryMoneyFlowSumByVo(int size,String  userId, String project,
                                                    String flowYear, String flowMonth, String flowType,
                                                    String proTypeSum,int page,int limit){
        page=(page-1)*limit;
        return financialService.queryMoneyFlowSumByVo(size,userId,project,flowYear,flowMonth,flowType,proTypeSum,page,limit);
    }

    /**
     *  根据条件查询分析项目请销款记录数
     *  @return 查询结果
     */
    @RequestMapping("/countMoneyFlowSumByVo")
    @ResponseBody
    public Map<String,Object> countMoneyFlowSumByVo(String userId, String project, String flowYear,
                                                    String flowMonth, String flowType, String proTypeSum){
        Map<String,Object> map =new HashMap<>();
        map.put("count",financialService.countMoneyFlowSumByVo(userId,project,flowYear,flowMonth,flowType,proTypeSum));
        return map;
    }

    /**
     *  校正请销记录
     *  @return 成功/失败
     */
    @RequestMapping("/editMoneyFlow")
    @ResponseBody
    public boolean editMoneyFlow(String userId, String project, String flowYear,
                                String flowMonth,  String flowMoney,String flowType, String proType){
        return financialService.editMoneyFlow(userId,project,flowYear,flowMonth,flowMoney,flowType,proType);
    }

}
