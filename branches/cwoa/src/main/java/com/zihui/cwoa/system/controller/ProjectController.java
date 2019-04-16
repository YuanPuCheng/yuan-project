package com.zihui.cwoa.system.controller;

import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.pojo.sys_project;
import com.zihui.cwoa.system.service.sys_projectService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/project")
public class ProjectController {

    private static Logger log = Logger.getLogger(ProjectController.class);

    @Resource
    private sys_projectService projectService;


    @RequestMapping(value = "/getproject")
    @ResponseBody
    public ConcurrentMap getuser(sys_project project){
        CallbackResult result = new CallbackResult();
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<sys_project> list  = projectService.selectProList(project);
        concurrentMap.put("count", list.size());
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }
}
