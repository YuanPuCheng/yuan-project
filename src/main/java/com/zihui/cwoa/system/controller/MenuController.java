package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.pojo.sys_menu;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_role_menuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/menu")
public class MenuController {


    private static Logger log = Logger.getLogger(MenuController.class);

    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_role_menuService roleMenuService;
    @Resource
    private sys_menuService menuService;

    @RequestMapping(value = "/add")
    @ResponseBody
    public CallbackResult add(sys_menu menu){
        CallbackResult result = new CallbackResult();
        menu.setTs(DateUtils.getDate());
        try {
            menuService.insertSelective(menu);
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
    public CallbackResult edit(sys_menu menu){
        CallbackResult result = new CallbackResult();
        //menu.setTs(DateUtils.getDate());
        try {
            menuService.updateByPrimaryKeySelective(menu);
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



    @RequestMapping(value = "/delete")
    @ResponseBody
    public CallbackResult delete(Integer menuId){
        CallbackResult result = new CallbackResult();
        //menu.setTs(DateUtils.getDate());
        try {
            menuService.deleteByPrimaryKey(menuId);
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
    public CallbackResult deletes(@RequestParam("menuIds") String menuIds){
        CallbackResult result = new CallbackResult();
       String [] menuId=  menuIds.split(",");

        try {
            for (String menuid:menuId){
                menuService.deleteByPrimaryKey(Integer.parseInt(menuid));
            }

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

    @RequestMapping(value = "/getmenu")
    @ResponseBody
    public List<sys_menu> getmenu(){

        List<sys_menu> menu = menuService.selectMenuList(new sys_menu());

        return menu;
    }

}
