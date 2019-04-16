package com.zihui.cwoa.system.service;


import com.github.pagehelper.PageHelper;
import com.zihui.cwoa.system.dao.sys_userMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.pojo.sys_user;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class sys_userService {

    @Resource
    private sys_userMapper userMapper;
    /**
     *  根据userid删除记录
     *  @param userId 传入userid
     *  @return 返回int 0 新增失败
     */
    public int deleteByPrimaryKey(Integer userId){
        return userMapper.deleteByPrimaryKey(userId);
    };

    /**
     *  根据user对象新增
     *  @param record
     *  @return 返回int 0 新增失败
     */
    public int insertSelective(sys_user record){
        return userMapper.insertSelective(record);
    };

    /**
     *  根据用户id查询 返回当前对象
     *  @param userId 传人userId
     *  @return sys_user对象
     */
    public sys_user selectByPrimaryKey(Integer userId){
        return userMapper.selectByPrimaryKey(userId);
    };

    /**
     *  根据用户id修改
     *  @param record 传人user对象
     *  @return 返回int 0 新增失败
     */
    public int updateByPrimaryKeySelective(sys_user record){
        return userMapper.updateByPrimaryKeySelective(record);
    };

    /**
     *  根据用户工号查询该工号是否存在
     *  @param usercode 用户工号
     *  @return sys_user 返回当前对象
     */
    public sys_user selectUserByLogin(String usercode){
        return userMapper.selectUserByLogin(usercode);
    };

    /**
     *  查询用户对应 部门
     *  @param userId 用户工号
     *  @return sys_user 返回当前对象
     */
    public sys_user selectDepartmentToUser(Integer userId){
        return userMapper.selectDepartmentToUser(userId);
    };
    /**
     *  根据用户工号查询该工号是否存在
     *  @param record 多条件查询
     *  @return sys_user 返回所有符合条件的对象
     */
    public List<sys_user> selectUserList(sys_user record){
        return userMapper.selectUserList(record);
    };

    /**
     *  用于分页
     *  @param record 多条件查询
     *  @return sys_user 返回所有符合条件的对象
     */
    public List<sys_user> selectUserList(sys_user record,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.selectUserList(record);
    };

    public List<sys_user> selectUserDepar(sys_user record){
        return userMapper.selectUserDepar(record);
    };

    /**
     *  删除用户
     *  @param userCode 用户工号
     *  @return int 删除的数量
     */
    public int deleteByUserCode(String userCode){
        return userMapper.deleteByUserCode(userCode);
    };
}
