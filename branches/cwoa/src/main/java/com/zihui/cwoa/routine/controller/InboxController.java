package com.zihui.cwoa.routine.controller;

import com.zihui.cwoa.routine.pojo.rw_mail;
import com.zihui.cwoa.routine.service.rw_mailService;
import com.zihui.cwoa.routine.service.rw_mail_userService;
import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import org.apache.ibatis.annotations.Param;
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
 * 收件箱Controller
 */
@Controller
@RequestMapping(value = "/inbox")
public class InboxController {

    private static Logger log = Logger.getLogger(InboxController.class);

    @Autowired
    private rw_mailService mailService;

    @Autowired
    private rw_mail_userService mail_userService;

    /**
     * 查看收件箱
     * @param userId
     * @param content
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getinbox")
    @ResponseBody
    public ConcurrentMap getinbox(@RequestParam("userId")Integer userId,
                                  @RequestParam( required = false)String content,
                                  Integer page,Integer limit){
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<rw_mail> rw_mails =  mailService.selectInbox(userId,content,page,limit);
        Integer count = mailService.selectInboxCount(userId,content);
        concurrentMap.put("count",count);
        concurrentMap.put("data", rw_mails);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    /**
     * 收件箱星标邮件
     * @param userId
     * @param mailIds
     * @param starState
     * @return
     */
    @RequestMapping(value = "/starmail")
    @ResponseBody
    public CallbackResult starmail(@RequestParam Integer userId,@RequestParam String mailIds,
                                   @RequestParam Integer starState){
        CallbackResult result = new CallbackResult();
        String[] mailIdss = mailIds.split(",");
        for(String mailId:mailIdss){
            if(!Basecommon.isNullStr(mailId)){
                mail_userService.updateToStarMail(userId,starState,Integer.parseInt(mailId));
            }
        }
        if(starState==1){
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
     *  不返回密送人
     * @param mailId
     * @return
     */
    @RequestMapping(value = "/getInboxInfo")
    @ResponseBody
    public rw_mail getInboxInfo(@RequestParam Integer mailId){
        rw_mail mail = mailService.selectInboxInfo(mailId);
        return  mail;
    }

    /**
     *  收件箱标记  是否查看邮件
     * @param mailIds  多个或一个
     * @param userId
     * @param lookState
     * @return
     */
    @RequestMapping(value = "/setLook")
    @ResponseBody
    public CallbackResult setLook(@RequestParam String mailIds,@RequestParam Integer userId,@RequestParam Integer lookState){
        CallbackResult result = new CallbackResult();
        String [] Ids = mailIds.split(",");
        for (String mailId:Ids){
            if(!Basecommon.isNullStr(mailId)){
                mail_userService.updateToLookState(userId,lookState,Integer.parseInt(mailId));
            }
        }
        if(lookState==1){
            result.setResult(200);
            result.setMessage("标记为未查看");
        }else{
            result.setResult(200);
            result.setMessage("标记为已查看");
        }

        return  result;
    }
}
