package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_fileMapper;
import com.zihui.cwoa.system.pojo.sys_file;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class sys_fileService {

    @Resource
    private sys_fileMapper fileMapper;

    public int deleteByPrimaryKey(Integer fileId){
        return fileMapper.deleteByPrimaryKey(fileId);
    };


    public int insertSelective(sys_file record){
        return fileMapper.insertSelective(record);
    };

    public sys_file selectByPrimaryKey(Integer fileId){
        return fileMapper.selectByPrimaryKey(fileId);
    };

    public int updateByPrimaryKeySelective(sys_file record){
        return fileMapper.updateByPrimaryKeySelective(record);
    };

    /**
     * 根据文件对象参数查询
     * @param record
     * @return list 返回list
     */
    public List<sys_file> selectFileList(sys_file record){
        return fileMapper.selectFileList(record);
    };
}
