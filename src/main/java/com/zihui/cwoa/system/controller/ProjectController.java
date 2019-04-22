package com.zihui.cwoa.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.pojo.sys_project;
import com.zihui.cwoa.system.service.sys_projectService;
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
@RequestMapping(value = "/project")
public class ProjectController {

    private static Logger log = Logger.getLogger(ProjectController.class);

    @Resource
    private sys_projectService projectService;


    @RequestMapping(value = "/getproject")
    @ResponseBody
    public ConcurrentMap getuser(sys_project project,Integer page, Integer limit){
        log.info(project.toString());
        log.info(page+"|||"+limit);
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<sys_project> list  = projectService.selectProList(project,page,limit);

        PageInfo<sys_project> pageInfo=new PageInfo<>(list);

        concurrentMap.put("count",pageInfo.getTotal());
        concurrentMap.put("data", pageInfo.getList());
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


    @RequestMapping(value = "/add")
    @ResponseBody
    public CallbackResult add(sys_project project){
        CallbackResult result = new CallbackResult();
        log.info(project.toString());
        try {
            projectService.insertSelective(project);
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
    public CallbackResult edit(sys_project project){
        CallbackResult result = new CallbackResult();
        log.info(project.toString());
        try {
            projectService.updateByPrimaryKeySelective(project);
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
    public CallbackResult edit(@RequestParam Integer projectId){
        CallbackResult result = new CallbackResult();
        log.info(projectId);
        try {
            projectService.deleteByPrimaryKey(projectId);
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
    public CallbackResult edit(@RequestParam String projectIds){
        CallbackResult result = new CallbackResult();
        log.info(projectIds);
        try {
            if(!Basecommon.isNullStr(projectIds)){
                String [] projectId = projectIds.split(",");
                for (String id:projectId){
                    projectService.deleteByPrimaryKey(Integer.parseInt(id));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("批量删除失败");
            return result;
        }

        result.setResult(200);
        result.setMessage("批量删除成功");

        return result;
    }
}
