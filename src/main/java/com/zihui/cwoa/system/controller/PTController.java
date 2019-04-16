package com.zihui.cwoa.system.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    public String loginlout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
    //修改密码
    @RequestMapping(value = "/updatepass")
    public String updatepass(){
        return "system/user/updatepass";
    }

}
