package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_user_departmentMapper;
import com.zihui.cwoa.system.pojo.sys_user_department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_user_departmentService {

    @Resource
    private sys_user_departmentMapper user_departmentMapper;

    /**
     *  根据id删除关联部门表记录
     *  @param id 传入id
     *  @return int 0=删除失败
     */
    public int deleteByPrimaryKey(Integer id){
        return user_departmentMapper.deleteByPrimaryKey(id);
    }
    /**
     *  新增用户关联部门表
     *  @param record 传入用户部门关联表
     *  @return int 0=新增失败
     */
    public int insert(sys_user_department record){
        return user_departmentMapper.insert(record);
    }

    /**
     *  根据id查询关联部门表记录
     *  @param id 传入id
     *  @return sys_user_department 对象
     */
    public sys_user_department selectByPrimaryKey(Integer id){
        return user_departmentMapper.selectByPrimaryKey(id);
    }
    /**
     *  根据用户管理部门表修改记录
     *  @param record 传入对象
     *  @return int 0=修改失败
     */
    public int updateByPrimaryKey(sys_user_department record){
        return user_departmentMapper.updateByPrimaryKey(record);
    }

    /**
     *  根据userid查询关联部门的id 包含多个部门角色
     *  @param userId 传入userid
     *  @return 返回List《Integer》部门id
     */
    public List<String> selectDepartmentByUserId(Integer userId){
        return user_departmentMapper.selectDepartmentByUserId(userId);
    }
}
