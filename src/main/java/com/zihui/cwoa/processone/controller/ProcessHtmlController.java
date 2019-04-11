package com.zihui.cwoa.processone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pro")
public class ProcessHtmlController {

    @RequestMapping("/askforleave")
    public String askForLeave(){
        return "process/askforleave";
    }

    @RequestMapping("/queryprocess")
    public String queryProcess(){
        return "process/queryprocess";
    }

    @RequestMapping("/querytask")
    public String queryTask(){
        return "process/querytask";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
