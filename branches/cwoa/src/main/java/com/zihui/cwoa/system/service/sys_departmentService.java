package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.dao.sys_departmentMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_departmentService {


    @Resource
    private sys_departmentMapper departmentMapper;


    /**
     *  根据部门id删除记录
     *  @param departmentId 传入部门id
     *  @return int 0=删除失败
     */
    public int deleteByPrimaryKey(String departmentId){
        return departmentMapper.deleteByPrimaryKey(departmentId);
    }

    /**
     *  新增部门角色
     *  @param record 传入部门对象
     *  @return int 0=新增失败
     */
    public int insertSelective(sys_department record){
        return departmentMapper.insertSelective(record);
    }

    /**
     *  根据菜单id查询部门
     *  @param departmentId 传入用户部门id
     *  @return 返回部门对象 1条记录
     */
    public sys_department selectByPrimaryKey(String departmentId){
        return departmentMapper.selectByPrimaryKey(departmentId);
    }
    /**
     *  根据部门id修改部门
     *  @param record 传入部门对象包含修改的部门id
     *  @return int 0=新增失败
     */
    public int updateByPrimaryKeySelective(sys_department record){
        return departmentMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *  根据部门id查询部门信息
     *  @param departmentId 传人多个或一个部门id
     *  @return List<sys_department> 返回一个或多个部门信息
     */
    public List<sys_department> selectDepartmentById(List<Integer> departmentId){
        return departmentMapper.selectDepartmentById(departmentId);
    }

    /**
     *  根据用户工号查询部门
     *  @param userId 用户工号
     *  @return sys_department 返回所有部门对象
     */
    public List<sys_department> selectDepartment(String userId){
        return departmentMapper.selectDepartment(userId);
    };


    public List<sys_department> selectMenu(List<String> departmentId){
        return departmentMapper.selectMenu(departmentId);
    };
}