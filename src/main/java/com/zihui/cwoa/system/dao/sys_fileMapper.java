package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_file;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@Mapper
public interface sys_fileMapper {
    int deleteByPrimaryKey(Integer fileId);


    int insertSelective(sys_file record);

    sys_file selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(sys_file record);

    List<sys_file> selectFileList(sys_file record);

    List<Map<String,Object>> queryFileNameById(String[] arr);

    List<sys_file> selectFileByProjectId(@Param("projectId")Integer projectId,@Param("page")Integer page,@Param("limit")Integer limit);

    Integer selectFileByProjectIdCount(Integer projectId);

}