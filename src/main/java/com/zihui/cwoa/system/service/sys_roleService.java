package com.zihui.cwoa.system.service;

import com.zihui.cwoa.system.dao.sys_roleMapper;
import com.zihui.cwoa.system.pojo.sys_role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_roleService {

    @Resource
    private sys_roleMapper roleMapper;

    public int deleteByPrimaryKey(Integer roleId){
        return roleMapper.deleteByPrimaryKey(roleId);
    };


    public int insertSelective(sys_role record){
        return roleMapper.insertSelective(record);
    };

    public sys_role selectByPrimaryKey(Integer roleId){
        return roleMapper.selectByPrimaryKey(roleId);
    };

    public int updateByPrimaryKeySelective(sys_role record){
        return roleMapper.updateByPrimaryKeySelective(record);
    };


    public List<sys_role> selectRoleList(Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return roleMapper.selectRoleList(page,limit);
    };

    public Integer selectRoleListCount(){
        return roleMapper.selectRoleListCount();
    };
    //展示角色下拉
    public List<sys_role> selectRolebySelect(Integer roleId){
        return roleMapper.selectRolebySelect(roleId);
    };
    //查询所有角色下包含的用户
    public List<sys_role>selectRoleToUser(Integer userId){
        return roleMapper.selectRoleToUser(userId);
    };
}

