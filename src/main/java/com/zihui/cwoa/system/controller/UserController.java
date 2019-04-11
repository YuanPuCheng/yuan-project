package com.zihui.cwoa.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_userService;
import com.zihui.cwoa.system.service.sys_user_departmentService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger log = Logger.getLogger(UserController.class);

    @Resource
    private sys_userService user_service;
    @Resource
    private sys_department_menuMapper department_menuMapper;
    @Resource
    private sys_departmentService departmentService;
    @Resource
    private sys_menuService menuService;
    @Resource
    private sys_user_departmentService user_departmentService;


    @RequestMapping(value = "/getuser")
    @ResponseBody
    public ConcurrentMap user(String usercode,String username,String sex,String email,
                         @RequestParam(defaultValue = "1")Integer pageStart,
                         @RequestParam(defaultValue = "1")Integer pageSize){

        sys_user user = new sys_user();
        user.setUserCode(usercode);
        user.setUserName(username);
        user.setTempVar1(sex);
        user.setEmail(email);
        List<sys_user> list = user_service.selectUserList(user,pageStart,pageSize);
        PageInfo<sys_user> info = new PageInfo<>(list);

        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        //PageInfo<sys_user> info = new PageInfo<>(list);
        concurrentMap.put("count", info.getTotal());
        concurrentMap.put("data", info.getList());
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    @RequestMapping(value = "/getuser1")
    @ResponseBody
    public ConcurrentMap sss(String usercode, String username, String sex, String email,
                             @RequestParam(defaultValue = "1")Integer pageNum,
                             @RequestParam(defaultValue = "1")Integer pageSize){
        CallbackResult result = new CallbackResult();
        sys_user user = new sys_user();
        user.setUserCode(usercode);
        user.setUserName(username);
        user.setTempVar1(sex);
        user.setEmail(email);
        List<sys_user> list = user_service.selectUserList(user);
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        //PageInfo<sys_user> info = new PageInfo<>(list);
        concurrentMap.put("count", 3);
        concurrentMap.put("data", list);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }



}
