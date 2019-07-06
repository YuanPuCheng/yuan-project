package com.zihui.cwoa.routine.service;

import com.zihui.cwoa.routine.dao.rw_mail_userMapper;
import com.zihui.cwoa.routine.pojo.rw_mail_user;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class rw_mail_userService {

    @Resource
    private rw_mail_userMapper mailUserMapper;

    public int deleteByPrimaryKey(Integer id){
        return mailUserMapper.deleteByPrimaryKey(id);
    };


    public int insertSelective(rw_mail_user record){
        return mailUserMapper.insertSelective(record);
    };

    public rw_mail_user selectByPrimaryKey(Integer id){
        return mailUserMapper.selectByPrimaryKey(id);
    };

    public int updateByPrimaryKeySelective(rw_mail_user record){
        return mailUserMapper.updateByPrimaryKeySelective(record);
    };
    //收件箱星标
    public int updateToStarMail(Integer userId,Integer starState,Integer mailId){
        return mailUserMapper.updateToStarMail(userId,starState,mailId);
    };
    //删除收件人信息
    public int delByMailId(Integer mailId){
        return mailUserMapper.delByMailId(mailId);
    };
    //标记已读或者未读
    public int updateToLookState(Integer userId,Integer lookState,Integer mailId){
        return mailUserMapper.updateToLookState(userId,lookState,mailId);
    };
}
