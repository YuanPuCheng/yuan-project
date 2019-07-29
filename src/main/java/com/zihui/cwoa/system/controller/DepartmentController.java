package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_role_menuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



@Controller
@RequestMapping(value = "/department")
public class DepartmentController {
    private static Logger log = Logger.getLogger(DepartmentController.class);
    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_role_menuService role_menuService;
    @Resource
    private sys_menuService menuService;

    @RequestMapping(value = "getdepartment")
    @ResponseBody
    public List<sys_department> getDepartment(sys_department department){

        List<sys_department> list =departmentService.selectByListSelect();

        return list;
    }



    @RequestMapping(value = "/getdepartmentPage")
    @ResponseBody
    public ConcurrentMap getDepartment(@RequestParam(value = "departmentName",defaultValue = "") String departmentName, Integer page, Integer limit) {
        ConcurrentMap concurrentMap = departmentService.selectDeperByPage(departmentName, page, limit);
        return concurrentMap;
    }





    @RequestMapping(value = "/add")
    @ResponseBody
    public CallbackResult add(sys_department department){

        CallbackResult result = new CallbackResult();
        department.setTs(DateUtils.getDate());
        log.info(department.toString());
        try {
            departmentService.insertSelective(department);
        }catch (Exception e){
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
    public CallbackResult edit(sys_department department){
        CallbackResult result = new CallbackResult();
        department.setTs(DateUtils.getDate());

        try {
            departmentService.updateByPrimaryKeySelective(department);
        }catch (Exception e){
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
    public CallbackResult del(@RequestParam Integer departmentId){
        log.info(departmentId);
        CallbackResult result = new CallbackResult();
        try {
            departmentService.deleteByPrimaryKey(departmentId);
        }catch (Exception e){
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
    public CallbackResult del(String departmentIds){
        CallbackResult result = new CallbackResult();
        String [] departments = departmentIds.split(",");
        for(String departmentId:departments){
            try {

                departmentService.deleteByPrimaryKey(Integer.parseInt(departmentId));


            }catch (Exception e){
                result.setResult(400);
                result.setMessage(departmentId+"删除失败");
                return result;
            }
        }
        result.setResult(200);
        result.setMessage("删除成功");
        return result;
    }




}
