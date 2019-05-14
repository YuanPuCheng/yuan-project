package com.zihui.cwoa.system.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/sys")
public class PTController {

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }


    @RequestMapping(value = "/index")
    @RequiresUser
    public String index(){
        return "index";
    }
    //注册页面
    @RequestMapping(value = "/redirect")
    public String redirect(){
        return "redirect";
    }
    //忘记密码页面
    @RequestMapping(value = "/forget")
    public String forget(){
        return "forget";
    }

    @RequestMapping(value = "/upload")
    public String up(){
        return "upload";
    }
    //基本资料
    @RequestMapping(value = "/userinfo")
    public String userinfo(){
        return "system/user/userinfo";
    }
    //用户退出
    @RequestMapping(value = "/logoutuser")
    public String loginlout(HttpSession session){
        session.removeAttribute("user");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
    //修改密码
    @RequestMapping(value = "/updatepass")
    public String updatepass(){
        return "system/user/updatepass";
    }
    //用户页面
    @RequestMapping(value = "/userManagement")
    public String user(){
        return "system/user/userManagement";
    }
    //用户新增页面
    @RequestMapping(value = "/addMangement")
    public String addMangement(){
        return "system/user/addMangement";
    }
    //用户修改页面
    @RequestMapping(value = "/reviseManagement")
    public String reviseManagement(){
        return "system/user/reviseManagement";
    }

    //menu跳转页面
    @RequestMapping(value = "/menu")
    public String menu(){
        return "system/menu/menu";
    }
    @RequestMapping(value = "/addmenu")
    public String addmenu(){
        return "system/menu/addmenu";
    }
    @RequestMapping(value = "/editmenu")
    public String editmenu(){
        return "system/menu/editmenu";
    }

    //部门跳转页面
    @RequestMapping(value = "/department")
    public String department(){
        return "system/department/department";
    }
    @RequestMapping(value = "/adddepartment")
    public String adddepartment(){
        return "system/department/adddepartment";
    }
    @RequestMapping(value = "/editdepartment")
    public String editdepartment(){
        return "system/department/editdepartment";
    }

    //项目跳转页面
    @RequestMapping(value = "/project")
    public String project(){
        return "system/project/project";
    }
    @RequestMapping(value = "/addproject")
    public String addproject(){
        return "system/project/addproject";
    }
    @RequestMapping(value = "/editproject")
    public String editproject(){
        return "system/project/editproject";
    }
    @RequestMapping(value = "/detailedproject")
    public String detailedproject(){
        return "system/project/detailedproject";
    }
    @RequestMapping(value = "/projecthetong")
    public String projecthetong(){
        return "system/project/projecthetong";
    }
    @RequestMapping(value = "/addhetong")
    public String addhetong(){
        return "system/project/addhetong";
    }

    //角色跳转页面
    @RequestMapping(value = "/role")
    public String role(){
        return "system/role/role";
    }
    @RequestMapping(value = "/addrole")
    public String addrole(){
        return "system/role/addrole";
    }
    @RequestMapping(value = "/editrole")
    public String editrole(){
        return "system/role/editrole";
    }
    //指派任务页面
    @RequestMapping(value = "/task")
    public String task(){
        return "system/task/task";
    }
    @RequestMapping(value = "/addtask")
    public String addtask(){
        return "system/task/addtask";
    }
    @RequestMapping(value = "/edittask")
    public String edittask(){
        return "system/task/edittask";
    }
    //我的任务页面
    @RequestMapping(value = "/mytask")
    public String mytask(){
        return "system/mytask/mytask";
    }
    @RequestMapping(value = "/mytaskinfo")
    public String mytaskinfo(){
        return "system/mytask/mytaskinfo";
    }
    @RequestMapping(value = "/editmytask")
    public String editmytask(){
        return "system/mytask/editmytask";
    }

    //首页
    @RequestMapping(value = "/syindex")
    public String index1(){
        return "home/index";
    }
}
