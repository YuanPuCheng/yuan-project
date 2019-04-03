package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryMapper {

    /**
     *  根据用户工号查询他发起的未完成的流程的ID
     *  @param userCode 用户工号
     *  @return 查询结果
     */
    public List<String> queryProNotActiveByUserCode(String userCode);

    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    public String queryProStatuByProInstanceId(String processInstanceId);
}
