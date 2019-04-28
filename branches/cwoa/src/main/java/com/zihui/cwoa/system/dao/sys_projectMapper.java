package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface sys_projectMapper {
    int deleteByPrimaryKey(Integer projectId);


    int insertSelective(sys_project record);

    sys_project selectByPrimaryKey(Integer projectId);

    int updateByPrimaryKeySelective(sys_project record);

    List<sys_project> selectProList(@Param("sys_project") sys_project record, @Param("page") Integer page, @Param("limit")Integer limit);

    Integer selectProListCount(@Param("sys_project") sys_project record);

    List<sys_project> projectListToSelect();

}