package com.zihui.cwoa.system.thread;

import com.zihui.cwoa.system.common.RedisUtils;

import javax.annotation.Resource;

public class DelRedisThread implements Runnable{

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void run() {
        try{
            redisUtils.del("getTq");
        }catch (Exception e){
            System.out.println("redis not conn");
        }

    }
}
