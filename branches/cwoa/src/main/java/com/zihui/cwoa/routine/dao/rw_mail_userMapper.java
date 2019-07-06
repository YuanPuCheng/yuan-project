package com.zihui.cwoa.routine.dao;

import com.zihui.cwoa.routine.pojo.rw_mail_user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface rw_mail_userMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(rw_mail_user record);

    rw_mail_user selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(rw_mail_user record);

    int updateToStarMail(@Param("userId")Integer userId,@Param("starState")Integer starState,@Param("mailId")Integer mailId);

    int updateToLookState(@Param("userId")Integer userId,@Param("lookState")Integer lookState,@Param("mailId")Integer mailId);

    int delByMailId(Integer mailId);

}