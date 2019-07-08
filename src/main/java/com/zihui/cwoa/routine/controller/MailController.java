package com.zihui.cwoa.routine.controller;


import com.zihui.cwoa.routine.pojo.rw_mail;
import com.zihui.cwoa.routine.pojo.rw_mail_user;
import com.zihui.cwoa.routine.service.rw_mailService;
import com.zihui.cwoa.routine.service.rw_mail_userService;
import com.zihui.cwoa.system.common.Basecommon;
import com.zihui.cwoa.system.common.CallbackResult;
import com.zihui.cwoa.system.common.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
@RequestMapping(value = "/mail")
public class MailController {

    private static Logger log = Logger.getLogger(MailController.class);
    @Autowired
    private rw_mailService mailService;

    @Autowired
    private rw_mail_userService mail_userService;


    /**
     * 发送邮件
     *
     *
     */
    @RequestMapping(value = "/sendmail")
    @ResponseBody
    public CallbackResult sendMail(rw_mail mail, @RequestParam("suser") String suser,
                                   @RequestParam("cuser") String cuser,
                                   @RequestParam("muser") String muser){
        CallbackResult result = new CallbackResult();
        log.info(mail.toString());
        log.info(suser);
        log.info(cuser);
        log.info(muser);
        //判断是不是从草稿箱过来的，
        if(mail.getMailId()==null){
            log.info("-----------insert mail-----------");
            mail.setSendTime(DateUtils.getDate());
            mail.setStarStatus(1);
            mailService.insertSelective(mail);
            String [] susers = suser.split(",");
            String [] cusers = cuser.split(",");
            String [] musers = muser.split(",");
            //添加收件人信息
            for (String id:susers){
                if(!Basecommon.isNullStr(id)){
                    rw_mail_user mail_user = new rw_mail_user();
                    mail_user.setMailId(mail.getMailId());
                    mail_user.setMailUser(Integer.parseInt(id));
                    mail_user.setLookState(1);//1未查看
                    mail_user.setStarState(1);//1不星标
                    mail_user.setStatus(1);//收件人
                    mail_userService.insertSelective(mail_user);
                }
            }
            //添加抄送人信息
            for (String id:cusers){
                if(!Basecommon.isNullStr(id)){
                    rw_mail_user mail_user = new rw_mail_user();
                    mail_user.setMailId(mail.getMailId());
                    mail_user.setMailUser(Integer.parseInt(id));
                    mail_user.setLookState(1);//1未查看
                    mail_user.setStarState(1);//1不星标
                    mail_user.setStatus(2);//抄送人
                    mail_userService.insertSelective(mail_user);
                }
            }
            //添加收件人信息
            for (String id:musers){
                if(!Basecommon.isNullStr(id)){
                    rw_mail_user mail_user = new rw_mail_user();
                    mail_user.setMailId(mail.getMailId());
                    mail_user.setMailUser(Integer.parseInt(id));
                    mail_user.setLookState(1);//1未查看
                    mail_user.setStarState(1);//1不星标
                    mail_user.setStatus(3);//密送人
                    mail_userService.insertSelective(mail_user);
                }
            }
        }else {
            log.info("-----------update mail-----------");
            mail.setSendTime(DateUtils.getDate());
            mail.setStarStatus(1);
            mailService.updateByPrimaryKeySelective(mail);
            mail_userService.delByMailId(mail.getMailId());//删除所有收件人信息
            String [] susers = suser.split(",");
            String [] cusers = cuser.split(",");
            String [] musers = muser.split(",");
            //重新添加收件人信息
            for (String id:susers){
                if(!Basecommon.isNullStr(id)){
                    rw_mail_user mail_user = new rw_mail_user();
                    mail_user.setMailId(mail.getMailId());
                    mail_user.setMailUser(Integer.parseInt(id));
                    mail_user.setLookState(1);//1未查看
                    mail_user.setStarState(1);//1不星标
                    mail_user.setStatus(1);//收件人
                    mail_userService.insertSelective(mail_user);
                }
            }
            //重新添加抄送人信息
            for (String id:cusers){
                if(!Basecommon.isNullStr(id)){
                    rw_mail_user mail_user = new rw_mail_user();
                    mail_user.setMailId(mail.getMailId());
                    mail_user.setMailUser(Integer.parseInt(id));
                    mail_user.setLookState(1);//1未查看
                    mail_user.setStarState(1);//1不星标
                    mail_user.setStatus(2);//抄送人
                    mail_userService.insertSelective(mail_user);
                }
            }
            //重新添加收件人信息
            for (String id:musers){
                if(!Basecommon.isNullStr(id)){
                    rw_mail_user mail_user = new rw_mail_user();
                    mail_user.setMailId(mail.getMailId());
                    mail_user.setMailUser(Integer.parseInt(id));
                    mail_user.setLookState(1);//1未查看
                    mail_user.setStarState(1);//1不星标
                    mail_user.setStatus(3);//密送人
                    mail_userService.insertSelective(mail_user);
                }
            }

        }

        if(mail.getState()==2){
            result.setResult(200);
            result.setMessage("发送成功");
        }else{
            result.setResult(200);
            result.setMessage("存草稿成功");
        }
        return result;
    }

    /**
     *  查询星标邮箱
     * @param userId
     * @param content
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getStarMail")
    @ResponseBody
    public ConcurrentMap getStarMail(@RequestParam("userId")Integer userId,
                                  @RequestParam( required = false)String content,
                                  Integer page, Integer limit){
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<rw_mail> rw_mails =  mailService.selectStarMail(userId,content,page,limit);
        Integer count = mailService.selectStarMailCount(userId,content);
        concurrentMap.put("count",count);
        concurrentMap.put("data", rw_mails);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    /**
     * 星标收件箱，星标，取消
     * @return
     */
    @RequestMapping(value = "/starmail")
    @ResponseBody
    public CallbackResult starmail( @RequestParam( required = false) String mailIdkeys,
                                    @RequestParam( required = false) String mailUserIdkeys,
                                    @RequestParam( required = false) String shuang,
                                    @RequestParam Integer userId){
        CallbackResult result = new CallbackResult();
        String[] mailIds1 = mailIdkeys.split(",");
        String[] mailIds2 = mailUserIdkeys.split(",");
        String[] mailIds3 = shuang.split(",");
        log.info(mailIdkeys);
        log.info(mailUserIdkeys);
        log.info(shuang);
        //取消收件箱星标
        for(String mailId:mailIds2){
            if(!Basecommon.isNullStr(mailId)){
                mail_userService.updateToStarMail(userId,1,Integer.parseInt(mailId));
            }
        }
        //取消发件箱星标
        for(String mailId:mailIds1){
            if(!Basecommon.isNullStr(mailId)){
                rw_mail mail = new rw_mail();
                mail.setStarStatus(1);
                mail.setMailId(Integer.parseInt(mailId));
                mailService.updateByPrimaryKeySelective(mail);
            }
        }
        //同时取消收件箱，发件箱星标
        for(String mailId:mailIds3){
            if(!Basecommon.isNullStr(mailId)){
                rw_mail mail1 = new rw_mail();
                mail1.setStarStatus(1);
                mail1.setMailId(Integer.parseInt(mailId));
                mailService.updateByPrimaryKeySelective(mail1);
                mail_userService.updateToStarMail(userId,1,Integer.parseInt(mailId));
            }
        }
            result.setResult(200);
            result.setMessage("取消成功");

        return result;
    }

    /**
     *  查看草稿箱
     * @param userId
     * @param content
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/getDrafts")
    @ResponseBody
    public ConcurrentMap getDrafts(@RequestParam("userId")Integer userId,
                                  @RequestParam( required = false)String content,
                                  Integer page, Integer limit){
        ConcurrentMap concurrentMap = new ConcurrentHashMap<String,Object>();
        List<rw_mail> rw_mails =  mailService.selectDrafts(userId,content,page,limit);
        Integer count = mailService.selectDraftsCount(userId,content);
        concurrentMap.put("count",count);
        concurrentMap.put("data", rw_mails);
        concurrentMap.put("code", 0);
        concurrentMap.put("msg", "成功");
        return concurrentMap;
    }

    /**
     * 删除草稿箱
     * @param mailIds
     * @return
     */
    @RequestMapping(value = "/delMail")
    @ResponseBody
    public CallbackResult delMail(@RequestParam String mailIds){
        CallbackResult result = new CallbackResult();
        String[] mailIdss = mailIds.split(",");
        for(String mailId:mailIdss){
            if(!Basecommon.isNullStr(mailId)){
                mail_userService.delByMailId(Integer.parseInt(mailId));
                mailService.deleteByPrimaryKey(Integer.parseInt(mailId));
            }
        }
        result.setResult(200);
        result.setMessage("删除成功");
        return result;
    }
}
