package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.pojo.sys_menu;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_department_menuService;
import com.zihui.cwoa.system.service.sys_menuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "department")
public class DepartmentController {
    private static Logger log = Logger.getLogger(DepartmentController.class);
    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_department_menuService department_menuService;
    @Resource
    private sys_menuService menuService;
    @RequestMapping(value = "getdepartment")
    @ResponseBody
    public ConcurrentMap getDepartment(sys_department department){
        ConcurrentMap concurrentMap = new ConcurrentHashMap();
        log.info(department.toString());
        List<sys_department> list =departmentService.selectDpeartmentList(department);
        concurrentMap.put("count", list.size());
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    @RequestMapping(value = "getdepartmentPage")
    @ResponseBody
    public ConcurrentMap getDepartment(@RequestParam(value = "departmentName",defaultValue = "") String departmentName, Integer page, Integer limit){

        ConcurrentMap concurrentMap = new ConcurrentHashMap();
        Map map = new HashMap();
        log.info(page+"|||"+limit+"|"+departmentName);
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        map.put("page",page);
        map.put("limit",limit);
        map.put("departmentName",departmentName);
        List<sys_department> list =departmentService.selectDpeartmentListPage(map);
        Integer count = departmentService.selectDpeartmentCount(departmentName);
        concurrentMap.put("count", count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public CallbackResult add(@RequestParam("departmentName") String departmentName,
                                @RequestParam("departmentCode") String departmentCode,
                                @RequestParam("status") String status,
                                @RequestParam("menuIds") String menuIds){

        CallbackResult result = new CallbackResult();
        sys_department department = new sys_department();
        department.setDepartmentName(departmentName);
        department.setDepartmentCode(departmentCode);
        department.setStatus(Integer.parseInt(status));
        department.setTs(DateUtils.getDate());
        log.info(department.toString());
        log.info(menuIds);
        try {
            departmentService.insertSelective(department);
            log.info("返回部门主键"+department.getDepartmentId());
            if(!Basecommon.isNullStr(menuIds)){
                String[] menu = menuIds.split(",");
                for(String menuId:menu){
                    department_menuService.insert(department.getDepartmentId(),Integer.parseInt(menuId));
                }

            }
        }catch (Exception e){
            result.setResult(400);
            result.setMessage("添加失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("添加成功");
        return result;
    }


    @RequestMapping(value = "edit")
    @ResponseBody
    public CallbackResult edit(@RequestParam("departmentId") Integer departmentId,
                               @RequestParam("departmentName") String departmentName,
                               @RequestParam("departmentCode") String departmentCode,
                               @RequestParam("status") String status,
                               @RequestParam("menuIds") String menuIds,
                               @RequestParam("oldmenuIds") String oldmenuIds){
        CallbackResult result = new CallbackResult();
        sys_department department = new sys_department();
        department.setDepartmentId(departmentId);
        department.setDepartmentName(departmentName);
        department.setDepartmentCode(departmentCode);
        department.setStatus(Integer.parseInt(status));
        department.setTs(DateUtils.getDate());
        log.info(department.toString());
        log.info(menuIds);
        log.info(oldmenuIds);
        Set<String> in = new HashSet();//新增
        Set<String> de = new HashSet();//删除
        try {
            departmentService.updateByPrimaryKeySelective(department);
            if(!Basecommon.isNullStr(menuIds)){
                String menu []= menuIds.split(",");//1,2
                String olds [] = oldmenuIds.split(",");//1


                Set set1 = new HashSet();
                Set set2 = new HashSet();

                for(String d:menu){
                    set1.add(d);
                }
                for (String dd:olds){
                    set2.add(dd);
                }
                in.addAll(set1);
                in.removeAll(set2);
                de.addAll(set2);
                de.removeAll(set1);

                log.info("新增：" + in);
                log.info("删除：" + de);
            }

        }catch (Exception e){
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }
        try {
            for (String menuid:de){
                int  count =department_menuService.deleteByDeparIdAndMenuId(departmentId,Integer.parseInt(menuid));
            }
            for (String menuid:in){
                int  count= department_menuService.insert(departmentId,Integer.parseInt(menuid));
            }
        }catch (Exception e){
            result.setResult(400);
            result.setMessage("添加权限失败");
            return result;

        }



        result.setResult(200);
        result.setMessage("修改成功");
        return result;
    }


    @RequestMapping(value = "del")
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


    @RequestMapping(value = "deletes")
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


    @RequestMapping(value = "menubydepar")
    @ResponseBody
    public CallbackResult menubydepar(Integer departmentId){
        CallbackResult result = new CallbackResult();
        List<Integer> list =null;
            try {

             list = department_menuService.selectMenuIdByDeparId(departmentId);

            }catch (Exception e){
                result.setResult(400);
                result.setMessage(departmentId+"查询失败");
                return result;
            }
        result.setResult(200);
        result.setMessage("查询成功");
        result.setList(list);
        return result;
    }



}
