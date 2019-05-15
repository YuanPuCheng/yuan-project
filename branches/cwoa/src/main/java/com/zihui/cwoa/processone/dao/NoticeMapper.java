package com.zihui.cwoa.processone.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeMapper {

    /**
     *  新增公告
     */
    int insertNotice(String title,String isTop,String text);

    /**
     *  查询公告
     */
    List<Map<String,Object>> queryNotice(int page,int num);

    /**
     *  查询公告总数
     */
    int countNotice();

    /**
     *  查询公告内容
     */
    String queryNoticeText(String id);

    /**
     *  删除公告
     */
    int deleteNotice(String id);

    /**
     *  批量删除公告
     */
    int deleteManyNotice(String[] split);

    /**
     *  编辑更新公告
     */
    int updateNotice(String title,String isTop,String text,String id);

    /**
     *  编辑数据回显
     */
    Map<String ,Object> queryNoticeById(String id);
}
