package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface QueryMapper {


    /**
     *  根据流程实例ID查询流程状态
     *  @param processInstanceId 流程实例ID
     *  @return 流程状态
     */
    String queryProStatuByProInstanceId(String processInstanceId);


    /**
     *  根据用户名查用户工号
     *  @param userName 用户名
     *  @return 用户工号
     */
    List<String> queryCodeByName(String userName);
}
