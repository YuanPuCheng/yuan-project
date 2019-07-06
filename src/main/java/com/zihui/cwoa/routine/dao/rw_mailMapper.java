package com.zihui.cwoa.routine.dao;

import com.zihui.cwoa.routine.pojo.rw_mail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface rw_mailMapper {

    int deleteByPrimaryKey(Integer mailId);


    int insertSelective(rw_mail record);

    rw_mail selectByPrimaryKey(Integer mailId);

    int updateByPrimaryKeySelective(rw_mail record);

    List<rw_mail> selectInbox(@Param("userId")Integer userId, @Param("content")String content,
                              @Param("page")Integer page, @Param("limit") Integer limit);

    Integer selectInboxCount(@Param("userId")Integer userId, @Param("content")String content);

    List<rw_mail> selectOutbox(@Param("userId")Integer userId, @Param("content")String content,
                               @Param("page")Integer page, @Param("limit") Integer limit);

    Integer selectOutboxCount(@Param("userId")Integer userId, @Param("content")String content);


    List<rw_mail> selectStarMail(@Param("userId")Integer userId, @Param("content")String content,
                                 @Param("page")Integer page, @Param("limit") Integer limit);

    Integer selectStarMailCount(@Param("userId")Integer userId, @Param("content")String content);

    List<rw_mail> selectDrafts(@Param("userId")Integer userId, @Param("content")String content,
                               @Param("page")Integer page, @Param("limit") Integer limit);

    Integer selectDraftsCount(@Param("userId")Integer userId, @Param("content")String content);

    rw_mail selectInboxInfo(@Param("mailId")Integer mailId);

    rw_mail selectOutboxInfo(@Param("mailId")Integer mailId);

    Integer selectNoLookCount(@Param("userId")Integer userId);
}