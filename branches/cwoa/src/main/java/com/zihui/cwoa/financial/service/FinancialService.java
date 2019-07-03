package com.zihui.cwoa.financial.service;

import com.zihui.cwoa.financial.dao.FinancialMapper;
import com.zihui.cwoa.financial.pojo.ProjectMonthDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinancialService {

    @Autowired
    private FinancialMapper financialMapper;

    /**
     *  根据条件查询请销款记录
     *  @return 查询结果
     */
    public Map<String,Object> queryMoneyFlowByVoc(int size, String  userCode, String project,
                                                  String flowYear, String flowMonth, String flowType,
                                                  int page,int num){
        List<ProjectMonthDetail> queryMoneyFlowByVoc =
                    financialMapper.queryMoneyFlowByVoc(userCode,project,flowYear,flowMonth,flowType,page,num);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",queryMoneyFlowByVoc);
        return map;
    }

    /**
     *  根据条件查询项目请销款记录数
     *  @return 查询结果
     */
    public Integer countMoneyFlowByVop(String  userCode, String project, String flowYear,
                                       String flowMonth, String flowType){
        return financialMapper.countMoneyFlowByVop(userCode,project,flowYear,flowMonth,flowType);
    }

    /**
     *  根据条件查询公司请销款记录数
     *  @return 查询结果
     */
    public Integer countMoneyFlowByVoc(String  userCode, String project, String flowYear,
                                       String flowMonth, String flowType){
        return financialMapper.countMoneyFlowByVoc(userCode,project,flowYear,flowMonth,flowType);
    }

    /**
     *  根据条件查询请销款记录
     *  @return 查询结果
     */
    public Map<String,Object> queryMoneyFlowByVop(int size, String  userCode, String project,
                                                  String flowYear, String flowMonth, String flowType,
                                                  int page,int num){
        List<ProjectMonthDetail> queryMoneyFlowByVop =
                financialMapper.queryMoneyFlowByVop(userCode,project,flowYear,flowMonth,flowType,page,num);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",queryMoneyFlowByVop);
        return map;
    }

    /**
     *  根据条件分析项目请销款记录
     *  @return 查询结果
     */
    public Map<String,Object> queryMoneyFlowSumByVo(int size,String  userCode, String project,
                                                    String flowYear, String flowMonth, String flowType,
                                                    String proType,int page,int num){
        List<ProjectMonthDetail> queryMoneyFlowSumByVo =
                financialMapper.queryMoneyFlowSumByVo(userCode,project,flowYear,flowMonth,flowType,proType,page,num);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",queryMoneyFlowSumByVo);
        return map;
    }

    /**
     *  根据条件查询分析项目请销款记录数
     *  @return 查询结果
     */
    public Integer countMoneyFlowSumByVo(String userCode, String project, String flowYear,
                                                    String flowMonth, String flowType, String proType){
        return financialMapper.countMoneyFlowSumByVo(userCode,project,flowYear,flowMonth,flowType,proType);
    }

    /**
     *  校正请销记录
     *  @return 成功/失败
     */
    public boolean editMoneyFlow(String userCode, String project, String flowYear,
                                 String flowMonth,  String flowMoney,String flowType, String proType){
        int count=financialMapper.editMoneyFlow(userCode,project,flowYear,flowMonth,flowMoney,flowType,proType);
        return (count>0);
    }
}
