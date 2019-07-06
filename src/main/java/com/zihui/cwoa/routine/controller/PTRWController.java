package com.zihui.cwoa.routine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/rw")
public class PTRWController {


    //发送邮件
    @RequestMapping(value = "/sendmail")
    public String sendmail(){
        return "routine/sendmail";
    }
    //收件箱
    @RequestMapping(value = "/inbox")
    public String inbox(){
        return "routine/inbox";
    }
    //打开收件箱邮件详情
    @RequestMapping(value = "/inboxinfo")
    public String inboxinfo(){
        return "routine/inbox/inboxinfo";
    }
    //回复收件箱邮件
    @RequestMapping(value = "/replymail")
    public String replymail(){
        return "routine/inbox/replymail";
    }
    //发件箱
    @RequestMapping(value = "/outbox")
    public String outbox(){
        return "routine/outbox";
    }
    //打开发件箱邮件详情
    @RequestMapping(value = "/outboxinfo")
    public String outboxinfo(){
        return "routine/outbox/outboxinfo";
    }
    //星标邮件
    @RequestMapping(value = "/starmail")
    public String starmail(){
        return "routine/starmail";
    }
    //打开星标邮件
    @RequestMapping(value = "/starmailinfo")
    public String starmailinfo(){
        return "routine/starmail/starmailinfo";
    }
    //草稿箱
    @RequestMapping(value = "/drafts")
    public String drafts(){
        return "routine/drafts";
    }
    //打开草稿箱
    @RequestMapping(value = "/draftsmail")
    public String draftsmail(){
        return "routine/drafts/draftsmail";
    }

}
