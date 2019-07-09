package com.zihui.cwoa.system.thread;

import com.zihui.cwoa.system.common.RedisUtils;
import com.zihui.cwoa.system.config.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



public class DelRedisThread implements Runnable{

    //@Autowired
    //private RedisUtils redisUtils;
    @Override
    public void run() {
        try{
            RedisUtils redisUtils= (RedisUtils) SpringUtil.getObject("redisUtils");
            redisUtils.del("getTq");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error redis not conn");
        }

    }
}
