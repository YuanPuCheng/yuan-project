package com.zihui.cwoa.financial.controller;

import com.zihui.cwoa.financial.service.FinancialService;
import com.zihui.cwoa.processone.service.QueryService;
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

    @Autowired
    private QueryService queryService;

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

    /**
     *  根据条件查询公司请销款记录
     *  @return 查询结果
     */
    @RequestMapping("/queryMoneyFlowByVoc")
    @ResponseBody
    public Map<String,Object> queryMoneyFlowByVoc(int size, String userCode, String project,
                                                  String flowYear, String flowMonth, String flowType,
                                                  int page, int limit){
        page=(page-1)*limit;
        return financialService.queryMoneyFlowByVoc(size,userCode,project,flowYear,flowMonth,flowType,page,limit);
    }

    /**
     *  根据条件查询项目请销款记录
     *  @return 查询结果
     */
    @RequestMapping("/queryMoneyFlowByVop")
    @ResponseBody
    public Map<String,Object> queryMoneyFlowByVop(int size, String userCode, String project,
                                                  String flowYear, String flowMonth, String flowType,
                                                  int page, int limit){
        page=(page-1)*limit;
        return financialService.queryMoneyFlowByVop(size,userCode,project,flowYear,flowMonth,flowType,page,limit);
    }

    /**
     *  根据条件查询项目请销款记录数
     *  @return 查询结果
     */
    @RequestMapping("/countMoneyFlowByVop")
    @ResponseBody
    public Map<String,Object> countMoneyFlowByVop(String name, String project, String flowYear,
                                       String flowMonth, String flowType){
        String userCode=null;
        Map<String,Object> map =new HashMap<>();
        if((name!=null) && (!name.equals(""))){
            userCode = queryService.queryCodeByName(name);
            if(userCode==null){
                map.put("count",0);
                return map;
            }
        }
        map.put("userCode",userCode);
        map.put("count",financialService.countMoneyFlowByVop(userCode,project,flowYear,flowMonth,flowType));
        return map;
    }

    /**
     *  根据条件查询公司请销款记录数
     *  @return 查询结果
     */
    @RequestMapping("/countMoneyFlowByVoc")
    @ResponseBody
    public Map<String,Object> countMoneyFlowByVoc(String name, String project, String flowYear,
                                                  String flowMonth, String flowType){
        String userCode=null;
        Map<String,Object> map =new HashMap<>();
        if((!"".equals(name)) && (name!=null)){
            userCode = queryService.queryCodeByName(name);
            if(userCode==null){
                map.put("count",0);
                return map;
            }
        }
        map.put("userCode",userCode);
        map.put("count",financialService.countMoneyFlowByVoc(userCode,project,flowYear,flowMonth,flowType));
        return map;
    }

    /**
     *  根据条件分析项目请销款记录
     *  @return 查询结果
     */
    @RequestMapping("/queryMoneyFlowSumByVo")
    @ResponseBody
    public Map<String,Object> queryMoneyFlowSumByVo(int size,String  userCode, String project,
                                                    String flowYear, String flowMonth, String flowType,
                                                    String proTypeSum,int page,int limit){
        page=(page-1)*limit;
        return financialService.queryMoneyFlowSumByVo(size,userCode,project,flowYear,flowMonth,flowType,proTypeSum,page,limit);
    }

    /**
     *  根据条件查询分析项目请销款记录数
     *  @return 查询结果
     */
    @RequestMapping("/countMoneyFlowSumByVo")
    @ResponseBody
    public Map<String,Object> countMoneyFlowSumByVo(String  name, String project, String flowYear,
                                                    String flowMonth, String flowType, String proTypeSum){
        String userCode=null;
        Map<String,Object> map =new HashMap<>();
        if((name!=null) && (!"".equals(name))){
            userCode = queryService.queryCodeByName(name);
            if(userCode==null){
                map.put("count",0);
                return map;
            }
        }
        map.put("userCode",userCode);
        map.put("count",financialService.countMoneyFlowSumByVo(userCode,project,flowYear,flowMonth,flowType,proTypeSum));
        return map;
    }
}
