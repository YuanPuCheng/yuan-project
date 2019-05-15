package com.zihui.cwoa.processone.controller;

import com.zihui.cwoa.processone.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeWorkController {

    @Autowired
    private NoticeService noticeService;

    /**
     *  新增公告
     *  @return text
     */
    @RequestMapping("/insertNotice")
    @ResponseBody
    public boolean insertNotice(String title,String isTop,String text){
        return noticeService.insertNotice(title,isTop,text);
    }

    /**
     *  查询公告
     *  @return json
     */
    @RequestMapping("/queryNotice")
    @ResponseBody
    public Map<String,Object> queryNotice(int size,int page,int limit){
        page=(page-1)*limit;
        return noticeService.queryNotice(size,page, limit);
    }

    /**
     *  查询公告总数
     *  @return text
     */
    @RequestMapping("/countNotice")
    @ResponseBody
    public int countNotice(){
        return noticeService.countNotice();
    }

    /**
     *  查询公告内容
     *  @return text
     */
    @RequestMapping("/queryNoticeText")
    @ResponseBody
    public String queryNoticeText(String id){
        return noticeService.queryNoticeText(id);
    }

    /**
     *  删除公告
     *  @return text
     */
    @RequestMapping("/deleteNotice")
    @ResponseBody
    public boolean deleteNotice(String id){
        return noticeService.deleteNotice(id);
    }

    /**
     *  批量删除公告
     *  @return text
     */
    @RequestMapping("/deleteManyNotice")
    @ResponseBody
    public boolean deleteManyNotice(String idArray){
        return noticeService.deleteManyNotice(idArray);
    }

    /**
     *  编辑更新公告
     *  @return text
     */
    @RequestMapping("/updateNotice")
    @ResponseBody
    public boolean updateNotice(String title,String isTop,String text,String id){
        return noticeService.updateNotice(title,isTop,text,id);
    }

    /**
     *  编辑数据回显
     *  @return json
     */
    @RequestMapping("/queryNoticeById")
    @ResponseBody
    public Map<String ,Object> queryNoticeById(String id){
        return noticeService.queryNoticeById(id);
    }
}
