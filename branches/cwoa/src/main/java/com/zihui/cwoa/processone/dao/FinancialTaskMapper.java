package com.zihui.cwoa.processone.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FinancialTaskMapper {

    void insertMoneyFlowOut(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    void insertMoneyFlowIn(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    void insertProMoneyFlowIn(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    void insertProMoneyFlowOut(String userCode,String userProject,String flowMoneyOut,String processInstanceId,String flowType);
}
