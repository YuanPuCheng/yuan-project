package com.zihui.cwoa.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import com.zihui.cwoa.system.pojo.pro_schedule;
import com.zihui.cwoa.system.pojo.sys_file;
import com.zihui.cwoa.system.pojo.sys_project;
import com.zihui.cwoa.system.service.pro_scheduleService;
import com.zihui.cwoa.system.service.sys_fileService;
import com.zihui.cwoa.system.service.sys_projectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.awt.SunHints;

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

    @Resource
    private pro_scheduleService scheduleService;

    @Resource
    private sys_fileService fileService;

    //查询所有项目，用于select 下拉框展示
    @RequestMapping(value = "/getprojecttoselect")
    @ResponseBody
    public List getprojecttoselect(){
        List<sys_project> list  = projectService.projectListToSelect();
        return list;
    }


    @RequestMapping(value = "/getproject")
    @ResponseBody
    public ConcurrentMap getuser(sys_project project,@RequestParam(required = false) Integer page, @RequestParam(required = false)Integer limit){
        log.info(project.toString());
        log.info(page+"|||"+limit);
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<sys_project> list  = projectService.selectProList(project,page,limit);

        log.info(list.toString());
        Integer count = projectService.selectProListCount(project);

        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


    @RequestMapping(value = "/add")
    @ResponseBody
    public CallbackResult add(@RequestBody sys_project project){
        CallbackResult result = new CallbackResult();
        log.info(project.toString());
        List<pro_schedule> schedules = project.getSchedules();

        try {
           projectService.insertSelective(project);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("添加失败");
            return result;
        }

        if(schedules.size()!=0){
            for (pro_schedule schedule:schedules){
                schedule.setProjectId(project.getProjectId());
                schedule.setTs(DateUtils.getDate());
                try {
                    scheduleService.insertSelective(schedule);
                }catch (Exception e){
                    e.printStackTrace();
                    result.setResult(400);
                    result.setMessage(project.getProjectName()+",项目进度添加失败");
                    return result;
                }

            }
        }

        result.setResult(200);
        result.setMessage("添加成功");

        return result;
    }


    @RequestMapping(value = "/edit")
    @ResponseBody
    public CallbackResult edit(@RequestBody sys_project project){
        CallbackResult result = new CallbackResult();
        log.info(project.toString());
        List<pro_schedule> schedules = project.getSchedules();
        try {
            projectService.updateByPrimaryKeySelective(project);
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(400);
            result.setMessage("修改失败");
            return result;
        }
        scheduleService.deleteByProjectId(project.getProjectId());
        for (pro_schedule schedule:schedules){
            schedule.setProjectId(project.getProjectId());
            schedule.setTs(DateUtils.getDate());
            try {
                scheduleService.insertSelective(schedule);
            }catch (Exception e){
                e.printStackTrace();
                result.setResult(400);
                result.setMessage(project.getProjectName()+",修改进度添加失败");
                return result;
            }
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

    @RequestMapping(value = "/projectfile")
    @ResponseBody
    public ConcurrentMap projectfile( Integer projectId,Integer page,Integer limit){
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<sys_file> list  = fileService.selectFileByProjectId(projectId,page,limit);

        Integer count = fileService.selectFileByProjectIdCount(projectId);

        concurrentMap.put("count",count);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

}
