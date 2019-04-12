package com.zihui.cwoa.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sys")
public class PTController {

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }


    @RequestMapping(value = "/index")
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

}
