package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HumanMapper {

    /**
     *  查询请假出差记录
     *  @return 查询结果
     */
    List<Map<String,Object>> queryLeaveByVo(String userCode, String project, String leaveYear,
                                            String leaveMonth, int page, int limit);

    /**
     *  查询请假出差记录条数
     *  @return 查询结果
     */
    Integer countLeaveByVo(String userCode, String project, String leaveYear, String leaveMonth);

    /**
     *  查询用户的出差请假记录详情
     *  @return 查询结果
     */
    List<Map<String,Object>> queryLeaveDetail(String userCode, String project, String leaveYear, String leaveMonth);
}
