package com.zihui.cwoa.routine.controller;


import com.zihui.cwoa.routine.pojo.rw_mail;
import com.zihui.cwoa.routine.service.rw_mailService;
import com.zihui.cwoa.routine.service.rw_mail_userService;
import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 已发送Controller
 */
@Controller
@RequestMapping(value = "/outbox")
public class OutboxController {
    private static Logger log = Logger.getLogger(OutboxController.class);

    @Autowired
    private rw_mailService mailService;

    @Autowired
    private rw_mail_userService mail_userService;


    /**
     * 查看已发送邮件
     * @param userId
     * @param content
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getoutbox")
    @ResponseBody
    public ConcurrentMap getinbox(@RequestParam("userId")Integer userId,
                                  @RequestParam( required = false)String content,
                                  Integer page, Integer limit){
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<rw_mail> rw_mails =  mailService.selectOutbox(userId,content,page,limit);
        Integer count = mailService.selectOutboxCount(userId,content);
        concurrentMap.put("count",count);
        concurrentMap.put("data", rw_mails);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }


    /**
     * 发件箱星标邮件
     * @param mailIds
     * @param starStatus
     * @return
     */
    @RequestMapping(value = "/starmail")
    @ResponseBody
    public CallbackResult starmail( @RequestParam String mailIds,
                                   @RequestParam Integer starStatus){
        CallbackResult result = new CallbackResult();
        String[] mailIdss = mailIds.split(",");
        for(String mailId:mailIdss){
            if(!Basecommon.isNullStr(mailId)){
                rw_mail mail = new rw_mail();
                mail.setStarStatus(starStatus);
                mail.setMailId(Integer.parseInt(mailId));
                mailService.updateByPrimaryKeySelective(mail);
            }
        }
        if(starStatus==1){
            result.setResult(200);
            result.setMessage("取消成功");
        }else{
            result.setResult(200);
            result.setMessage("标星成功");
        }

        return result;
    }

    /**
     *  根据邮件id返回邮件信息
     *  返回密送人
     * @param mailId
     * @return
     */
    @RequestMapping(value = "/getOutboxInfo")
    @ResponseBody
    public rw_mail getInboxInfo(@RequestParam Integer mailId){
        rw_mail mail = mailService.selectOutboxInfo(mailId);
        return  mail;
    }

}
