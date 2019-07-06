package com.zihui.cwoa.routine.service;


import com.zihui.cwoa.routine.dao.rw_mailMapper;
import com.zihui.cwoa.routine.pojo.rw_mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class rw_mailService {

    @Resource
    private rw_mailMapper mailMapper;

    public int deleteByPrimaryKey(Integer mailId){
        return mailMapper.deleteByPrimaryKey(mailId);
    };


    public int insertSelective(rw_mail record){
        return mailMapper.insertSelective(record);
    };

    public rw_mail selectByPrimaryKey(Integer mailId){
        return mailMapper.selectByPrimaryKey(mailId);
    };

    public int updateByPrimaryKeySelective(rw_mail record){
        return  mailMapper.updateByPrimaryKeySelective(record);
    };
    //收件箱
    public List<rw_mail> selectInbox(Integer userId, String content,
                                     Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return mailMapper.selectInbox(userId,content,page,limit);
    };
    //收件箱总数
    public Integer selectInboxCount(Integer userId, String content){
        return mailMapper.selectInboxCount(userId,content);
    }
    //已发送
    public List<rw_mail> selectOutbox(Integer userId, String content,
                               Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return mailMapper.selectOutbox(userId,content,page,limit);
    };
    //已发送总数
    public Integer selectOutboxCount(Integer userId,String content){
        return mailMapper.selectOutboxCount(userId,content);
    };
    //查看星标邮件
    public List<rw_mail> selectStarMail(Integer userId, String content,
                                      Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return mailMapper.selectStarMail(userId,content,page,limit);
    };
    //星标邮件总数
    public Integer selectStarMailCount(Integer userId,String content){
        return mailMapper.selectStarMailCount(userId,content);
    };
    //查看草稿箱
    public List<rw_mail> selectDrafts(Integer userId, String content,
                                        Integer page, Integer limit){
        if(page==1){
            page =0;
        }else{
            page = (page-1)*limit;
        }
        return mailMapper.selectDrafts(userId,content,page,limit);
    };
    //草稿箱总数
    public Integer selectDraftsCount(Integer userId,String content){
        return mailMapper.selectDraftsCount(userId,content);
    };
    //根据邮件id查询收件箱单个邮件//不返回密送人信息
    public rw_mail selectInboxInfo(Integer mailId){
        return mailMapper.selectInboxInfo(mailId);
    };
    //根据邮件id查询收件箱单个邮件//返回密送人信息
    public rw_mail selectOutboxInfo(Integer mailId){
        return mailMapper.selectOutboxInfo(mailId);
    };
    //查找未读邮件总数
    public Integer selectNoLookCount(Integer userId){
        return mailMapper.selectNoLookCount(userId);
    };
}
