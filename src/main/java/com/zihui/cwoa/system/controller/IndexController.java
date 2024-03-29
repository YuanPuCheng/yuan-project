package com.zihui.cwoa.system.controller;


import com.zihui.cwoa.processone.service.QueryService;
import com.zihui.cwoa.routine.service.rw_mailService;
import com.zihui.cwoa.system.pojo.sys_project;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_projectService;
import com.zihui.cwoa.system.service.sys_taskSerivce;
import com.zihui.cwoa.system.service.sys_userService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
@Controller
@RequestMapping(value = "/index")
public class IndexController {
    public static Logger logger = Logger.getLogger(IndexController.class);
    @Autowired
    private sys_taskSerivce taskService;
    @Autowired
    private QueryService queryService;
    @Autowired
    private sys_projectService projectService;
    @Autowired
    private rw_mailService mailService;
    @Autowired
    private sys_userService userService;




    //查询所有任务
    @RequestMapping("/taskCountAll")
    @ResponseBody
    public ConcurrentMap myTaskAll(HttpSession session) throws Exception {
        ConcurrentMap map = new ConcurrentHashMap();
        sys_user user = (sys_user) session.getAttribute("user");
        if(user==null){
                throw new Exception("当前用户未登录，请重新登录");
        }
        ExecutorService pool = Executors.newCachedThreadPool();//创建一个线程池
        //创建待办任务线程
        Callable<Integer> task1= new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return queryService.queryTaskCountById(user.getUserId());
            }
        };
        //创建我的通知线程
        Callable<Integer> task2= new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return taskService.myTaskCount(user.getUserId());
            }
        };
        //创建项目图表线程
        Callable<Map> task3= new Callable<Map>() {
            @Override
            public Map call() throws Exception {

                return echarsiIndex();
            }
        };
        //创建未读邮件线程
        Callable<Integer> task4= new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mailService.selectNoLookCount(user.getUserId());
            }
        };
        FutureTask<Integer> fu1 = new FutureTask(task1);//待办任务总数
        FutureTask<Integer> fu2 = new FutureTask(task2);//我的通知总数
        FutureTask<Map> fu3 = new FutureTask(task3);//天气图表
        FutureTask<Integer> fu4 = new FutureTask(task4);//未读邮件总数
        pool.execute(fu1);
        pool.execute(fu2);
        pool.execute(fu3);
        pool.execute(fu4);
        try {
            Integer count1=fu1.get();
            Integer count2=fu2.get();
            Map m = fu3.get();
            Integer count4=fu4.get();
           // Map tq = fu4.get();
            map.put("tcount",count1);
            map.put("mcount",count2);
            map.put("projectEchar",m);
            map.put("inboxCount",count4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }

        return map;
    }


   @RequestMapping("/getTq")
   @ResponseBody
    public Map httpClient(){
        return userService.getTp();
    }
  /* @RequestMapping("/getTq")
   @ResponseBody
    public Map httpClient1(){
        Map map = new HashMap();
        RestTemplate template = new RestTemplate();
       //template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
       Map map1 = new HashMap();
       map1.put("citykey", "101240101");
       String data = template.getForObject(TQURL+"?citykey={citykey}", String.class,map1);
       System.out.println(data.toString());
        //JSONObject json =JSONObject.parseObject(data.getBody().toString());//将接口返回的数据转为JSON
        //
        *//*JSONObject json1 =(JSONObject)json.get("data");
        JSONArray array =(JSONArray)json1.get("forecast");
        List<String> da =new  ArrayList();
        List<Integer> height =new  ArrayList();
        List<Integer> di =new  ArrayList();
        for(int i=0;i<array.size();i++){
            JSONObject object = (JSONObject)array.get(i);
            String high =(String) object.get("high");
            String d =(String) object.get("low");
            high = high.substring(high.indexOf(" ")+1,high.indexOf("."));
            d=d.substring(d.indexOf(" ")+1,d.indexOf("."));
            da.add(object.get("date").toString());
            height.add(Integer.parseInt(high));
            di.add(Integer.parseInt(d));
        }
        map.put("data",da);
        map.put("high",height);
        map.put("low",di);*//*

        return map;
    }*/



    public Map echarsiIndex(){
        List list = new ArrayList();
        List list1 = new ArrayList();
        Map map1 = new HashMap();
        Map map2 = new HashMap();
        Map map3 = new HashMap();

        Map map4 = new HashMap();
        Map map5 = new HashMap();
        Map map6 = new HashMap();
        List<sys_project> projects =projectService.projectListToSelect();
        int wwc=0;
        int jxz=0;
        int ywc=0;
        for (sys_project project:projects){
            if(project.getStatus()==1){
                wwc++;
            }else if(project.getStatus()==2){
                jxz++;
            }else if(project.getStatus()==3){
                ywc++;
            }
        }
        map1.put("name","未开始");
        map1.put("value",wwc);
        map2.put("name","进行中");
        map2.put("value",jxz);
        map3.put("name","已完成");
        map3.put("value",ywc);
        list.add(map1);
        list.add(map2);
        list.add(map3);

        map4.put("name","未开始");
        map5.put("name","进行中");
        map6.put("name","已完成");
        list1.add(map4);
        list1.add(map5);
        list1.add(map6);
        Map m = new HashMap();
        m.put("namevalue",list);
        m.put("name",list1);
        return m;
    }


}
