package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.HumanService;
import com.zihui.cwoa.processone.service.QueryService;
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

    @Autowired
    private QueryService queryService;

    /**
     *  查询请假出差记录
     *  @return 查询结果
     */
    @RequestMapping("/queryLeaveByVo")
    @ResponseBody
    public Map<String,Object> queryLeaveByVo(int size,String userCode, String project, String leaveYear,
                                             String leaveMonth, int page, int limit){
        page=(page-1)*limit;
        return humanService.queryLeaveByVo(size,userCode,project,leaveYear,leaveMonth,page,limit);
    }

    /**
     *  查询请假出差记录条数
     *  @return 查询结果
     */
    @RequestMapping("/countLeaveByVo")
    @ResponseBody
    public Map<String,Object> countLeaveByVo(String userName, String project,
                                             String leaveYear, String leaveMonth){
        String userCode=null;
        Map<String,Object> map =new HashMap<>();
        if(userName!=null && !userName.equals("")){
            userCode = queryService.queryCodeByName(userName);
            if(userCode==null){
                map.put("count",0);
                return map;
            }
        }
        map.put("userCode",userCode);
        map.put("count",humanService.countLeaveByVo(userCode,project,leaveYear,leaveMonth));
        return map;
    }

    /**
     *  查询用户的出差请假记录详情
     *  @return 查询结果
     */
    @RequestMapping("/queryLeaveDetail")
    @ResponseBody
    public List<Map<String,Object>> queryLeaveDetail(String userCode, String project,
                                                     String leaveYear, String leaveMonth){
        return humanService.queryLeaveDetail(userCode,project,leaveYear,leaveMonth);
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
     *  根据父ID查询菜单名称数组
     *  @return text
     */
    @RequestMapping("/queryMenuByParentId")
    @ResponseBody
    public List<String> queryMenuByParentId(String userId,String parentId){
        return humanService.queryMenuByParentId(userId,parentId);
    }
}
