package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.pojo.sys_menu;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_department_menuService;
import com.zihui.cwoa.system.service.sys_menuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        //sys_department department = new sys_department();
        log.info(department.toString());
        List<sys_department> list =departmentService.selectDpeartmentList(department);
        concurrentMap.put("count", list.size());
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");

        return concurrentMap;
    }

    @RequestMapping(value = "getmenu")
    @ResponseBody
    public Set getmenu(sys_department department){
        ConcurrentMap concurrentMap = new ConcurrentHashMap();
        List<Integer> menuid = department_menuService.selectMenuIdByUserId(13);
        List<sys_menu> menu = menuService.selectMenuList(new sys_menu());
        //List<sys_menu> menu = menuService.selectMenuByMenuId(menuid);
        //
        Set<sys_menu> set = Common.getmenu(menu);

        /*for(sys_menu menu1 :menu){
            sys_menu m = new sys_menu();
            log.info(menu1.toString());
            if(menu1.getParentId()==0){
                m=menu1;
                //menu.remove(m);
            }
            if(m.getMenuId()!=null){
                List<sys_menu> list = new ArrayList();

                for (sys_menu menu2 :menu){
                    sys_menu mm = new sys_menu();
                    if(m.getMenuId()==menu2.getParentId()){
                        mm = menu2;


                        if(mm.getMenuId()!=null){
                            List<sys_menu> list1 = new ArrayList();
                            for (sys_menu menu3 :menu){

                                if(mm.getMenuId()==menu3.getParentId()){
                                    list1.add(menu3);
                                }
                            }
                            mm.setMenus(list1);
                        }

                            list.add(mm);


                    }
                }
                if(list.size()!=0){
                    m.setMenus(list);
                }
            }
            if(m.getMenuId()!=null){
                set.add(m);
            }
        }
*/
        return set;
    }



}
