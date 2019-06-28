package com.zihui.cwoa.processone.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FinancialTaskMapper {

    void insertMoneyFlowOut(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    void insertMoneyFlowIn(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    void insertProMoneyFlowIn(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType);

    void insertProMoneyFlowOut(String userId,String userProject,String flowMoneyOut,String processInstanceId,String flowType);
}
