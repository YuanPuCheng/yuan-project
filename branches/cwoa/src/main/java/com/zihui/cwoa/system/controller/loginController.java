package com.zihui.cwoa.system.controller;

import com.zihui.cwoa.system.common.Common;
import com.zihui.cwoa.system.dao.sys_department_menuMapper;
import com.zihui.cwoa.system.pojo.sys_department;
import com.zihui.cwoa.system.pojo.sys_menu;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_departmentService;
import com.zihui.cwoa.system.service.sys_menuService;
import com.zihui.cwoa.system.service.sys_userService;
import com.zihui.cwoa.system.service.sys_user_departmentService;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.omg.CORBA.Request;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class loginController {
    private static Logger log = Logger.getLogger(loginController.class);

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


    @RequestMapping(value = "/login")
    public ModelAndView login(){
        ModelAndView view = new ModelAndView();
        view.addObject("name","admin");

        view.addObject("11","11");
        view.setViewName("login");

        return view;
    }

    @RequestMapping(value = "/file")
    public ModelAndView f(){
        ModelAndView view = new ModelAndView();
        view.setViewName("filetwo");
        return view;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public String t(@RequestBody Map m,HttpServletRequest request){
        String  ip =Common.getIpAddr(request);
        log.info(m.toString());
        log.info(ip);
        return "22";
    }

    @RequestMapping(value = "/loginuser" , method = RequestMethod.POST)
    public ModelAndView userlogin(@RequestParam String usercode, @RequestParam String password, Model model) {
        ModelAndView view =new ModelAndView();
        System.out.println("进入验证");
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(usercode, password,false);
            System.out.println(token);
            SecurityUtils.getSubject().login(token);


        } catch ( UnknownAccountException uae ) {
            log.warn("用户帐号不正确");
            view.addObject("status", 400);
            view.addObject("message", "用户帐号不正确");
            view.setViewName("index");
            return view;

        } catch ( IncorrectCredentialsException ice ) {
            log.warn("用户密码不正确");
            view.addObject("status", 400);
            view.addObject("message", "用户密码不正确");
            view.setViewName("index");
            return view;

        } catch ( LockedAccountException lae ) {
            log.warn("用户帐号被锁定");
            view.addObject("status", 400);
            view.addObject("message", "用户帐号被锁定");
            view.setViewName("index");
            return view;
        }
        view.addObject("status", 200);
        view.addObject("message", "登录成功");
        view.setViewName("index");
            return view;
    }

    @RequestMapping(value = "/logoutuser")
    @ResponseBody
    public String loginlout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "退出成功";
    }


    @RequestMapping(value = "/add")
    @RequiresRoles("1")
    @ResponseBody
    public String add (){
        System.out.println("进入add方法");

        return "add方法";
    }

    @RequestMapping(value = "/delete")
    @RequiresRoles("delete1")
    @ResponseBody
    public String delete (){
        System.out.println("delete1");

        return "delete1方法";
    }


    @RequestMapping( value = "/insert")
    @RequiresPermissions("2")
    @ResponseBody
    public String insert (){
        System.out.println("进入insert方法");

        return "insert方法";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser")
    @ResponseBody
    public Map error(){
        Map map =new HashMap();
       // List<sys_department> department=departmentService.selectDepartment("1");
        sys_user user1 =user_service.selectDepartmentToUser("1");
        List<String> s = new ArrayList<>();
        s.add("1");
        s.add("2");
        List<sys_department> ss = departmentService.selectMenu(s);
        map.put("user1",user1);
        map.put("sys_department",ss);
        return map;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/index")
    @ResponseBody
    public Map d(){
        Map map =new HashMap();
        List<Integer> a = new ArrayList<>();
        a.add(1);
         List<sys_menu> m =menuService.selectMenuByMenuId(a);
        map.put("m",m);
        return map;
    }



}
