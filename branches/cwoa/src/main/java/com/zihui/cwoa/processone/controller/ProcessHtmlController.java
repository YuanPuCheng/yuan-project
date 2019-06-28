package com.zihui.cwoa.processone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  流程相关页面
 */
@Controller
@RequestMapping("/pro")
public class ProcessHtmlController {

    @RequestMapping("/askForLeave")
    public String askForLeave(){
        return "process/askForLeave";
    }

    @RequestMapping("/queryProcess")
    public String queryProcess(){
        return "process/queryProcess";
    }

    @RequestMapping("/queryTask")
    public String queryTask(){
        return "process/queryTask";
    }

    @RequestMapping("/queryEndProcess")
    public String queryEndProcess(){
        return "process/queryEndProcess";
    }

    @RequestMapping("/createProcess")
    public String createProcess(){
        return "process/createProcess";
    }

    @RequestMapping("/askForBusiness")
    public String askForBusiness(){
        return "process/askForBusiness";
    }

    @RequestMapping("/askForReimburse")
    public String askForReimburse(){
        return "process/askForReimburse";
    }

    @RequestMapping("/queryProcessByVo")
    public String queryProcessByVo(){
        return "process/queryProcessByVo";
    }

    @RequestMapping("/liveProcess")
    public String liveProcess(){
        return "process/liveProcess";
    }

    @RequestMapping("/askForMoney")
    public String askForMoney(){
        return "process/askForMoney";
    }

    @RequestMapping("/askForProReimburse")
    public String askForProReimburse(){
        return "process/askForProReimburse";
    }

    @RequestMapping("/askForProMoney")
    public String askForProMoney(){
        return "process/askForProMoney";
    }

    @RequestMapping("/queryCheckProcessHtml")
    public String queryCheckProcessHtml(){
        return "process/queryCheckProcess";
    }
}
