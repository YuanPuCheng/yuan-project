package com.zihui.cwoa.processone.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  公告相关页面
 */
@Controller
@RequestMapping("/notice")
public class NoticeHtmlController {

    @RequestMapping("/addNoticeHtml")
    public String addNoticeHtml(){
        return "process/notice/addNotice";
    }

    @RequestMapping("/manageNoticeHtml")
    public String manageNoticeHtml(){
        return "process/notice/manageNotice";
    }
}
