package com.zihui.cwoa.financial.dao;

import com.zihui.cwoa.financial.pojo.ProjectMonthDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FinancialMapper {

    /**
     *  根据条件查询公司请销款记录
     *  @return 查询结果
     */
    List<ProjectMonthDetail> queryMoneyFlowByVoc(String  userCode, String project, String flowYear,
                                                 String flowMonth, String flowType,int page, int num);

    /**
     *  根据条件查询公司请销款记录数
     *  @return 查询结果
     */
    Integer countMoneyFlowByVoc(String  userCode, String project, String flowYear, String flowMonth,
                                String flowType);

    /**
     *  根据条件查询项目请销款记录
     *  @return 查询结果
     */
    List<ProjectMonthDetail> queryMoneyFlowByVop(String  userCode, String project, String flowYear,
                                                 String flowMonth, String flowType,int page, int num);

    /**
     *  根据条件查询项目请销款记录数
     *  @return 查询结果
     */
    Integer countMoneyFlowByVop(String  userCode, String project, String flowYear, String flowMonth,
                                String flowType);

    /**
     *  根据条件分析项目请销款记录
     *  @return 查询结果
     */
    List<ProjectMonthDetail> queryMoneyFlowSumByVo(String userCode, String project, String flowYear,
                                  String flowMonth, String flowType,String proType,int page, int num);

    /**
     *  根据条件查询分析项目请销款记录数
     *  @return 查询结果
     */
    Integer countMoneyFlowSumByVo(String userCode, String project, String flowYear,
                                  String flowMonth, String flowType, String proType);

    /**
     *  校正请销记录
     *  @return 插入的记录数
     */
    Integer editMoneyFlow(String userCode, String project, String flowYear,
                          String flowMonth,  String flowMoney,String flowType, String proType);
}
