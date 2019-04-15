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
    List<String> queryProcessActiveByUserCode(String userCode);

    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    String queryProStatuByProInstanceId(String processInstanceId);

    /**
     *  根据流程实例ID查询流程名称
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    String queryProNameByProInstanceId(String processInstanceId);

    /**
     *  根据用户名查用户工号
     *  @param userName 用户名
     *  @return 用户工号
     */
    List<String> queryCodeByName(String userName);
}
