package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaveTaskMapper {

    /**
     *  插入请假 出差记录
     *  @param list 记录集合
     */
    void insertLeave(List<String> list);
}
