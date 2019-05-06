package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.system.pojo.sys_role;
import com.zihui.cwoa.system.pojo.sys_task;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_taskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/task")
public class taskController {

    @Resource
    private sys_taskService taskService;


    @RequestMapping(value = "/gettaskPage")
    @ResponseBody
    public ConcurrentMap gettaskPage(Integer page, Integer limit, HttpSession session){
        ConcurrentMap concurrentMap =new ConcurrentHashMap();
        sys_user user =(sys_user) session.getAttribute("user");
        List<sys_task> list = taskService.selectZhiPaiTask(46,page,limit);
        Integer count = taskService.selectZhiPaiTaskCount(46);
        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


}
