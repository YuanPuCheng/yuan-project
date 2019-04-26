package com.zihui.cwoa.processone.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FinancialTaskMapper {

    Integer insertMoneyFlowOut(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    Integer insertMoneyFlowIn(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType);
}
