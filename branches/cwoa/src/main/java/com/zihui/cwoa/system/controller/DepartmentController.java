package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.service.sys_departmentService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "department")
public class DepartmentController {


    private static Logger log = Logger.getLogger(DepartmentController.class);

    @Resource
    private sys_departmentService departmentService;


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

}
