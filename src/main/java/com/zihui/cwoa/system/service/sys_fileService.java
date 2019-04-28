package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_fileMapper;
import com.zihui.cwoa.system.pojo.sys_file;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    }

    public sys_file selectByPrimaryKey(Integer fileId){
        return fileMapper.selectByPrimaryKey(fileId);
    }

    public int updateByPrimaryKeySelective(sys_file record){
        return fileMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据文件对象参数查询
     * @param record
     * @return list 返回list
     */
    public List<sys_file> selectFileList(sys_file record){
        return fileMapper.selectFileList(record);
    }

    public List<Map<String,Object>> queryFileNameById(String idArray){
        String[] arr = idArray.split(",");
        return fileMapper.queryFileNameById(arr);
    }

    /**
     * 查询项目合同附件 分页
     * @param projectId 项目查询
     * @return list 返回list
     */
    public List<sys_file> selectFileByProjectId(Integer projectId,Integer page,Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return fileMapper.selectFileByProjectId(projectId,page,limit);
    };

    //查询合同附件总数
    public Integer selectFileByProjectIdCount(Integer projectId){
        return fileMapper.selectFileByProjectIdCount(projectId);
    };
}
