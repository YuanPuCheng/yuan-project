package com.zihui.cwoa.system.controller;


import org.apache.shiro.authz.annotation.RequiresUser;
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

    @RequestMapping(value = "/redirect")
    public String redirect(){
        return "redirect";
    }

    @RequestMapping(value = "/forget")
    public String forget(){
        return "forget";
    }

    @RequestMapping(value = "/upload")
    public String up(){
        return "upload";
    }

}
