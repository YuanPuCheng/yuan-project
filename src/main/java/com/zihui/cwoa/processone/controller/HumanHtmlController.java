package com.zihui.cwoa.processone.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  人事相关页面
 */
@Controller
@RequestMapping("/hum")
public class HumanHtmlController {

    @RequestMapping("/queryLeaveByVoHtml")
    public String queryLeaveByVoHtml(){
        return "process/human/queryLeaveByVo";
    }
}
