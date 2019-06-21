package com.zihui.cwoa.workplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/plan")
public class PlanHtmlController {

    @RequestMapping("/workPlanHtml")
    public String workPlanHtml(){
        return "workplan/workPlan";
    }

    @RequestMapping("/workPlanHelpHtml")
    public String workPlanHelpHtml(){
        return "workplan/workPlanHelp";
    }
}
