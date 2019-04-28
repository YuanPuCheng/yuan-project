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

    @RequestMapping("/queryendprocess")
    public String queryEndProcess(){
        return "process/queryendprocess";
    }

    @RequestMapping("/createprocess")
    public String createProcess(){
        return "process/createprocess";
    }

    @RequestMapping("/askforbusiness")
    public String askForBusiness(){
        return "process/askforbusiness";
    }

    @RequestMapping("/askforreimburse")
    public String askForReimburse(){
        return "process/askforreimburse";
    }

    @RequestMapping("/queryprocessbyvo")
    public String queryProcessByVo(){
        return "process/queryprocessbyvo";
    }

    @RequestMapping("/liveprocess")
    public String liveProcess(){
        return "process/liveprocess";
    }

    @RequestMapping("/askformoney")
    public String askForMoney(){
        return "process/askformoney";
    }

    @RequestMapping("/askforproreimburse")
    public String askForProReimburse(){
        return "process/askforproreimburse";
    }
}
