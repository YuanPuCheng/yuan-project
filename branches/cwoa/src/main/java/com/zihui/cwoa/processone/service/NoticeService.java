package com.zihui.cwoa.processone.service;

import com.zihui.cwoa.processone.dao.NoticeMapper;
import com.zihui.cwoa.system.common.RedisUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@CacheConfig(cacheNames = {"NoticeServiceCache"})
@Service
public class NoticeService {

    public static Logger logger = Logger.getLogger(NoticeService.class);

    @Autowired
    private NoticeMapper noticeMapper;

    @Resource
    private RedisUtils redisUtils;

    /**
     *  新增公告
     */
    public boolean insertNotice(String title,String isTop,String text){
        try {
            redisUtils.deleteCache("queryNotice*");
        }catch (Exception e){
            logger.error(e);
        }
        return noticeMapper.insertNotice(title,isTop,text)>0;
    }

    /**
     *  查询公告
     */
    @Cacheable(key="'queryNotice'+#p1+#p2")
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
        try {
            redisUtils.deleteCache("queryNotice*");
        }catch (Exception e){
            logger.error(e);
        }
        return noticeMapper.deleteNotice(id)>0;
    }

    /**
     *  批量删除公告
     */
    public boolean deleteManyNotice(String idArray){
        try {
            redisUtils.deleteCache("queryNotice*");
        }catch (Exception e){
            logger.error(e);
        }
        String[] split = idArray.split(",");
        return (noticeMapper.deleteManyNotice(split)>0);
    }

    /**
     *  编辑更新公告
     */
    public boolean updateNotice(String title,String isTop,String text,String id){
        try {
            redisUtils.deleteCache("queryNotice*");
        }catch (Exception e){
            logger.error(e);
        }
        return (noticeMapper.updateNotice(title,isTop,text,id)>0);
    }

    /**
     *  编辑数据回显
     */
    public Map<String ,Object> queryNoticeById(String id){
        return noticeMapper.queryNoticeById(id);
    }

}
