package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.pojo.sys_role;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.pojo.sys_users;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_roleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/role")
public class RoleController {

    private static Logger log = Logger.getLogger(RoleController.class);
    @Resource
    private sys_roleService roleService;
    @Resource
    private sys_menuService menuService;


    @RequestMapping(value = "/roleselect")
    @ResponseBody
    public List<sys_role> roleselect(@RequestParam(required = false)Integer roleId){

        return roleService.selectRolebySelect(roleId);
    }


    @RequestMapping(value = "/getrolePage")
    @ResponseBody
    public ConcurrentMap getrolePage( Integer page, Integer limit){
        ConcurrentMap concurrentMap =new ConcurrentHashMap();

        List<sys_role> list = roleService.selectRoleByPage(page,limit);
        Integer count = roleService.selectRoleByPageCount();
        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


    @RequestMapping(value = "/add")
    @ResponseBody
    public CallbackResult add(@RequestParam String roleName,@RequestParam Integer roleLevel,
                                @RequestParam Integer roleParentId, @RequestParam String menuId){

        CallbackResult result =roleService.add(roleName,roleLevel,roleParentId,menuId);

        return result;
    }


    @RequestMapping(value = "/edit")
    @ResponseBody
    public CallbackResult edit( @RequestParam Integer roleId,@RequestParam String menuIds,
                                @RequestParam String roleName,@RequestParam Integer roleLevel,
                               @RequestParam Integer roleParentId, @RequestParam String oldmenuIds){


        sys_role role = new sys_role();
        role.setRoleId(roleId);
        role.setRoleLevel(roleLevel);
        role.setRoleParentId(roleParentId);
        role.setRoleName(roleName);

        CallbackResult result = roleService.edit(role,menuIds,oldmenuIds);

        return result;
    }
    @RequestMapping(value = "/del")
    @ResponseBody
    public CallbackResult del( Integer roleId){
        CallbackResult result =roleService.del(roleId);
        return result;
    }


    @RequestMapping(value = "/deletes")
    @ResponseBody
    public CallbackResult deletes( String roleIds){
        CallbackResult result =roleService.deletes(roleIds);
        return result;
    }

    @RequestMapping(value = "menubyrole")
    @ResponseBody
    public CallbackResult menubyrole(Integer roleId){
        CallbackResult result = new CallbackResult();
        List<Integer> list =null;
        try {

            list = menuService.selectMenuIdByroleId(roleId);

        }catch (Exception e){
            result.setResult(400);
            result.setMessage("查询失败");
            return result;
        }
        result.setResult(200);
        result.setMessage("查询成功");
        result.setList(list);
        return result;
    }




    @RequestMapping(value = "/getroleUser")
    @ResponseBody
    public ConcurrentMap getroleUser(HttpSession session){
        ConcurrentMap concurrentMap =new ConcurrentHashMap();
       sys_user userinfo =(sys_user) session.getAttribute("user");
        List<sys_role> roles = roleService.selectRoleToUser(userinfo.getUserId());
        List list = new ArrayList();
        for(sys_role role:roles){
            Map map = new HashMap();
            map.put("type","optgroup");
            map.put("name",role.getRoleName());
            list.add(map);
            for(sys_users user:role.getUsers()){
                Map map2 = new HashMap();
                map2.put("name",user.getUserName());
                map2.put("value",user.getUserId());
                list.add(map2);
            }

        }
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "success");
        return concurrentMap;
    }


}
