package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.financial.pojo.RoleAllUser;
import com.zihui.cwoa.processone.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/human")
public class HumanWorkController {

    @Autowired
    private HumanService humanService;

    /**
     *  查询请假出差记录
     *  @return 查询结果
     */
    @RequestMapping("/queryLeaveByVo")
    @ResponseBody
    public Map<String,Object> queryLeaveByVo(int size,String userId, String project, String leaveYear,
                                             String leaveMonth, int page, int limit){
        page=(page-1)*limit;
        return humanService.queryLeaveByVo(size,userId,project,leaveYear,leaveMonth,page,limit);
    }

    /**
     *  查询请假出差记录条数
     *  @return 查询结果
     */
    @RequestMapping("/countLeaveByVo")
    @ResponseBody
    public Map<String,Object> countLeaveByVo(String userId, String project,
                                             String leaveYear, String leaveMonth){
        Map<String,Object> map =new HashMap<>();
        map.put("count",humanService.countLeaveByVo(userId,project,leaveYear,leaveMonth));
        return map;
    }

    /**
     *  查询用户的出差请假记录详情
     *  @return 查询结果
     */
    @RequestMapping("/queryLeaveDetail")
    @ResponseBody
    public List<Map<String,Object>> queryLeaveDetail(String userId, String project,
                                                     String leaveYear, String leaveMonth){
        return humanService.queryLeaveDetail(userId,project,leaveYear,leaveMonth);
    }

    /**
     *  查询所有角色及该角色下的所有用户
     *  @return json
     */
    @RequestMapping("/queryRoleAllUser")
    @ResponseBody
    public Map<String,Object> queryRoleAllUser(){
        return humanService.queryRoleAllUser();
    }

    /**
     *  根据用户ID及父ID查询他拥有的三级权限名称数组
     *  @return text
     */
    @RequestMapping("/queryMenuByParentId")
    @ResponseBody
    public List<String> queryMenuByParentId(String userId,String parentId){
        return humanService.queryMenuByParentId(userId,parentId);
    }

    /**
     *  查询角色用以下拉框选择
     *  @return json
     */
    @RequestMapping("/queryRoleSelect")
    @ResponseBody
    public List<Map<String,Object>> queryRoleSelect(){
        return humanService.queryRoleSelect();
    }

    /**
     *  查询所有角色及该角色下的所有用户
     *  @return json
     */
    @RequestMapping("/roleUser")
    @ResponseBody
    public List<RoleAllUser> roleUser(){
        return humanService.roleUser();
    }

    /**
     *  查询所有用户名字和部门
     *  @return json
     */
    @RequestMapping("/queryNameDepartment")
    @ResponseBody
    public List<Map<String,Object>> queryNameDepartment(){
        return humanService.queryNameDepartment();
    }
}
