package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.financial.pojo.RoleAllUser;
import com.zihui.cwoa.processone.dao.HumanMapper;
import com.zihui.cwoa.system.pojo.sys_users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CacheConfig(cacheNames = {"HumanServiceCache"})
@Service("HumanService")
public class HumanService {

    @Autowired
    private HumanMapper humanMapper;

    /**
     *  查询请假出差记录
     *  @return 查询结果
     */
    public Map<String,Object> queryLeaveByVo(int size,String userId, String project, String leaveYear,
                                             String leaveMonth, int page, int limit){
        List<Map<String,Object>> queryLeaveByVo =
                humanMapper.queryLeaveByVo(userId,project,leaveYear,leaveMonth,page,limit);
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",queryLeaveByVo);
        return map;
    }

    /**
     *  查询请假出差记录条数
     *  @return 查询结果
     */
    public Integer countLeaveByVo(String userId, String project, String leaveYear, String leaveMonth){
        return humanMapper.countLeaveByVo(userId,project,leaveYear,leaveMonth);
    }

    /**
     *  查询用户的出差请假记录详情
     *  @return 查询结果
     */
    public List<Map<String,Object>> queryLeaveDetail(String userId, String project,
                                               String leaveYear, String leaveMonth){
        return humanMapper.queryLeaveDetail(userId,project,leaveYear,leaveMonth);
    }

    /**
     *  查询所有角色及该角色下的所有用户
     */
    @Cacheable(key="'userQueryRoleAllUser'")
    public Map<String,Object> queryRoleAllUser(){
        List<RoleAllUser> roleAllUsers = humanMapper.queryRoleAllUser();
        List<Map<String,Object>> dataList=new ArrayList<>();
        Map<String,Object> dataMap=new HashMap<>();
        for (RoleAllUser role: roleAllUsers) {
            Map<String,Object> map=new HashMap<>();
            map.put("name",role.getRole_name());
            map.put("type","optgroup");
            dataList.add(map);
            for(sys_users user:role.getList()){
                Map<String,Object> map2=new HashMap<>();
                map2.put("name",user.getUserName());
                map2.put("value",user.getUserId());
                dataList.add(map2);
            }
        }
        dataMap.put("data", dataList);
        dataMap.put("code", 0);
        dataMap.put("msg", "未知错误");
        return dataMap;
    }

    /**
     *  根据父ID查询菜单名称数组
     */
    public List<String> queryMenuByParentId(String userId,String parentId){
        return humanMapper.queryMenuByParentId(userId,parentId);
    }

    /**
     *  查询角色用以下拉框选择
     */
    @Cacheable(key="'roleQueryRoleSelectChw'")
    public List<Map<String,Object>> queryRoleSelect(){
        return humanMapper.queryRoleSelect();
    }

    /**
     *  查询所有角色及该角色下的所有用户
     */
    @Cacheable(key="'userChwRoleUser'")
    public List<RoleAllUser> roleUser(){
        return humanMapper.queryRoleAllUser();
    }

    /**
     *  查询用户部门
     */
    public String selectDepartmentById(String userId){
        return humanMapper.selectDepartmentById(userId);
    }

    /**
     *  查询用户姓名及部门
     */
    @Cacheable(key="'userQueryNameDepartment'")
    public List<Map<String,Object>> queryNameDepartment(){
        return humanMapper.queryNameDepartment();
    }
}
