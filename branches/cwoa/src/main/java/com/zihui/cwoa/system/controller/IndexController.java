package com.zihui.cwoa.system.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zihui.cwoa.processone.service.QueryService;
import com.zihui.cwoa.system.pojo.sys_project;
import com.zihui.cwoa.system.pojo.sys_user;
import com.zihui.cwoa.system.service.sys_projectService;
import com.zihui.cwoa.system.service.sys_taskSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
    @Autowired
    private sys_taskSerivce taskService;
    @Autowired
    private QueryService queryService;
    @Autowired
    private sys_projectService projectService;



    public final String URL = "http://t.weather.sojson.com/api/weather/city/101240101";//天气接口1
    public final String TQURL = "http://wthrcdn.etouch.cn/weather_mini?citykey=101240101";//天气接口2

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
                return queryService.queryTaskCountByCode(user.getUserCode());
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
        //创建天气图表线程
        /*Callable<Map> task4= new Callable<Map>() {
            @Override
            public Map call() throws Exception {

                return httpClient1();
            }
        };*/
        FutureTask<Integer> fu1 = new FutureTask(task1);
        FutureTask<Integer> fu2 = new FutureTask(task2);
        FutureTask<Map> fu3 = new FutureTask(task3);
        //FutureTask<Map> fu4 = new FutureTask(task4);
        pool.execute(fu1);
        pool.execute(fu2);
        pool.execute(fu3);
        //pool.execute(fu4);
        try {
            Integer count1=fu1.get();
            Integer count2=fu2.get();
            Map m = fu3.get();
           // Map tq = fu4.get();
            map.put("tcount",count1);
            map.put("mcount",count2);
            map.put("projectEchar",m);
            //map.put("tq",tq);
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
        Map map = new HashMap();
        RestTemplate template = new RestTemplate();
        ResponseEntity data =template.getForEntity(URL,String.class);
        JSONObject json =JSONObject.parseObject(data.getBody().toString());//将接口返回的数据转为JSON
        JSONObject json1 =(JSONObject)json.get("data");
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
            da.add(object.get("date").toString()+"号");
            height.add(Integer.parseInt(high));
            di.add(Integer.parseInt(d));
        }
        map.put("data",da);
        map.put("high",height);
        map.put("low",di);

        return map;
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
