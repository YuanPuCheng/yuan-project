package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.NoticeMapper;
import com.zihui.cwoa.system.common.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@CacheConfig(cacheNames = {"NoticeServiceCache"})
@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Resource
    private RedisUtils redisUtils;

    /**
     *  新增公告
     */
    public boolean insertNotice(String title,String isTop,String text){
        redisUtils.deleteCache("queryNotice*");
        return (noticeMapper.insertNotice(title,isTop,text)>0);
    }

    /**
     *  查询公告
     */
    @Cacheable(key="'queryNotice'+#p0+#p1+#p2")
    public Map<String,Object> queryNotice(int size,int page,int num){
        Map<String,Object> map =new HashMap<>();
        map.put("code",0);
        map.put("msg","请求成功");
        map.put("count",size);
        map.put("data",noticeMapper.queryNotice(page,num));
        return map;
    }

    /**
     *  查询公告数量
     */
    @Cacheable(key="'queryNoticeCount'")
    public int countNotice(){
        return noticeMapper.countNotice();
    }

    /**
     *  查询公告内容
     */
    public String queryNoticeText(String id){
        return noticeMapper.queryNoticeText(id);
    }

    /**
     *  删除公告
     */
    public boolean deleteNotice(String id){
        redisUtils.deleteCache("queryNotice*");
        return noticeMapper.deleteNotice(id)>0;
    }

    /**
     *  批量删除公告
     */
    public boolean deleteManyNotice(String idArray){
        redisUtils.deleteCache("queryNotice*");
        String[] split = idArray.split(",");
        return (noticeMapper.deleteManyNotice(split)>0);
    }

    /**
     *  编辑更新公告
     */
    public boolean updateNotice(String title,String isTop,String text,String id){
        redisUtils.deleteCache("queryNotice*");
        return (noticeMapper.updateNotice(title,isTop,text,id)>0);
    }

    /**
     *  编辑数据回显
     */
    public Map<String ,Object> queryNoticeById(String id){
        return noticeMapper.queryNoticeById(id);
    }

}
