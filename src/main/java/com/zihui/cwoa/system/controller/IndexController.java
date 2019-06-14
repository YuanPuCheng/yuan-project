package com.zihui.cwoa.system.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/index")
public class IndexController {
    public String URL = "http://t.weather.sojson.com/api/weather/city/101240101";


    @RequestMapping(value = "getTq")
    @ResponseBody
    public Map httpClient(){
        Map map = new HashMap();
        RestTemplate template = new RestTemplate();
        ResponseEntity data =template.getForEntity(URL,String.class);
        JSONObject json =JSONObject.parseObject(data.getBody().toString());
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

            System.out.println(high);
            System.out.println(object);
            da.add(object.get("date").toString()+"号");
            height.add(Integer.parseInt(high));
            di.add(Integer.parseInt(d));
        }
        map.put("data",da);
        map.put("high",height);
        map.put("low",di);

        return map;
    }
}
