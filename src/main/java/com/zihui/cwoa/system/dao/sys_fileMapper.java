package com.zihui.cwoa.system.dao;

import com.zihui.cwoa.system.pojo.sys_file;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface sys_fileMapper {
    int deleteByPrimaryKey(Integer fileId);


    int insertSelective(sys_file record);

    sys_file selectByPrimaryKey(Integer fileId);

    int updateByPrimaryKeySelective(sys_file record);

    List<sys_file> selectFileList(sys_file record);
}