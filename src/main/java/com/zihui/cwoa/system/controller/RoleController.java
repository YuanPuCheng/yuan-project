package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.pojo.sys_role;
import com.zihui.cwoa.system.service.sys_roleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/role")
public class RoleController {
    @Resource
    private sys_roleService roleService;


    @RequestMapping(value = "/roleselect")
    @ResponseBody
    public List<sys_role> roleselect( ){

        return roleService.selectRolebySelect();
    }


    @RequestMapping(value = "/getrolePage")
    @ResponseBody
    public ConcurrentMap getrolePage( Integer page, Integer limit){
        ConcurrentMap concurrentMap =new ConcurrentHashMap();

        List<sys_role> list = roleService.selectRoleList(page,limit);
        Integer count = roleService.selectRoleListCount();
        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


    @RequestMapping(value = "/add")
    @ResponseBody
    public CallbackResult add( sys_role role){
        CallbackResult result =new CallbackResult();
        try {
            roleService.insertSelective(role);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("添加失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("添加成功");
        return result;
    }


    @RequestMapping(value = "/edit")
    @ResponseBody
    public CallbackResult edit( sys_role role){
        CallbackResult result =new CallbackResult();
        try {
            roleService.updateByPrimaryKeySelective(role);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("修改成功");
        return result;
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public CallbackResult del( Integer roleId){
        CallbackResult result =new CallbackResult();
        try {
            roleService.deleteByPrimaryKey(roleId);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("删除失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("删除成功");
        return result;
    }


    @RequestMapping(value = "/deletes")
    @ResponseBody
    public CallbackResult deletes( String roleIds){
        CallbackResult result =new CallbackResult();


            String []id = roleIds.split(",");
            for (String roleId:id){
                if(!Basecommon.isNullStr(roleId)){
                    try {
                        roleService.deleteByPrimaryKey(Integer.parseInt(roleId));
                    }catch (Exception e){
                        e.printStackTrace();
                        result.setResult(400);
                        result.setMessage("删除失败");
                        return result;
                    }

                }
            }


        result.setResult(200);
        result.setMessage("删除成功");
        return result;
    }



}
