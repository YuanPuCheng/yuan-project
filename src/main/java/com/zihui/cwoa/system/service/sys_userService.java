package com.zihui.cwoa.system.service;


import com.zihui.cwoa.system.dao.sys_userMapper;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.pojo.sys_users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class sys_userService {

    @Resource
    private sys_userMapper userMapper;


    public int deleteByPrimaryKey(Integer userId){
        return userMapper.deleteByPrimaryKey(userId);
    };

    public int insertSelective(sys_user record){
        return userMapper.insertSelective(record);
    };

    public sys_user selectByPrimaryKey(sys_user record){
        return userMapper.selectByPrimaryKey(record);
    };

    public int updateByPrimaryKeySelective(sys_user record){
        return userMapper.updateByPrimaryKeySelective(record);
    };

    public sys_user selectUserByCode(String userCode){
        return userMapper.selectUserByCode(userCode);
    };

    /**
     *  根据条件查询当前角色、项目下所有用户
     *  @param roleId 角色id  可空
     *  @param projectId 项目id 可空
     *  @return list
     */
    public List<sys_users> userRoleQuery(Integer roleId, Integer projectId){
        return userMapper.userRoleQuery(roleId,projectId);
    };

    public sys_user selectUserInfo(Integer userId){
        return userMapper.selectUserInfo(userId);
    };
    public List<sys_user> selectUserByPage(sys_user user,Integer page,Integer limit){
        return userMapper.selectUserByPage(user,page,limit);
    };

    public Integer selectUserByPageCount(sys_user user){
        return userMapper.selectUserByPageCount(user);
    };

    public List<sys_users> selectUserAndProject(){
        return userMapper.selectUserAndProject();
    };


    public List<sys_users> selectUserBySelect(){
        return userMapper.selectUserBySelect();
    };
}
